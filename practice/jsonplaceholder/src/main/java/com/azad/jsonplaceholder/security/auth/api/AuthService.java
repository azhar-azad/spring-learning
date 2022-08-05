package com.azad.jsonplaceholder.security.auth.api;

import com.azad.jsonplaceholder.models.dtos.MemberDto;
import com.azad.jsonplaceholder.repos.MemberRepository;
import com.azad.jsonplaceholder.repos.RoleRepository;
import com.azad.jsonplaceholder.security.entities.MemberEntity;
import com.azad.jsonplaceholder.security.entities.RoleEntity;
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

        if (roleName.equalsIgnoreCase("ADMIN")) { // Someone is trying to create an Admin user.
            MemberEntity loggedInMember = getLoggedInMember(); // An Admin user has to be logged-in to make such request.
            if (!loggedInMember.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
                // Logged-in user is not an Admin
                throw new RuntimeException("Only admins can create a new Admin Member. '"
                        + loggedInMember.getUsername() + "' is not an Admin");
            }
        }

        String encodedPass = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPass);

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

    public MemberEntity getLoggedInMember() {
        String username = getLoggedInUsername();

        return memberRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Member not found with username: " + username));
    }

    public String getLoggedInUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
