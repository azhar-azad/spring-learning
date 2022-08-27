package com.azad.ListShare.security.auth;

import com.azad.ListShare.models.dtos.MemberDto;
import com.azad.ListShare.models.entities.MemberEntity;
import com.azad.ListShare.models.entities.RoleEntity;
import com.azad.ListShare.repos.MemberRepository;
import com.azad.ListShare.repos.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(MemberRepository memberRepository, RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    public MemberDto registerMember(MemberDto memberDto) throws RuntimeException {

        String roleName = memberDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // someone is trying to create an Admin user
            MemberEntity loggedInMember = getLoggedInMember(); // An admin user has to be logged-in to make such request.
            if (!loggedInMember.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
                throw new RuntimeException("Only admins can create a new Admin member. '"
                        + loggedInMember.getUsername() + "' is not an Admin.");
            }
        }

        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPassword);

        RoleEntity roleEntity = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        MemberEntity memberEntity = modelMapper.map(memberDto, MemberEntity.class);
        memberEntity.setRole(roleEntity);
        memberEntity.setEnabled(true);
        memberEntity.setExpired(false);
        memberEntity.setLocked(false);

        return modelMapper.map(memberRepository.save(memberEntity), MemberDto.class);
    }

    public void authenticateMember(String username, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken);
    }

    public boolean loggedInUserIsAdmin() {
        MemberEntity loggedInMember = getLoggedInMember();

        String roleName = loggedInMember.getRole().getRoleName();

        return roleName.equalsIgnoreCase("ADMIN");
    }

    public MemberEntity getLoggedInMember() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return memberRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Member not found with username: " + username));
    }
}
