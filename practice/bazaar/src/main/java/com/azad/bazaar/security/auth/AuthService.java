package com.azad.bazaar.security.auth;

import com.azad.bazaar.models.dtos.MemberDto;
import com.azad.bazaar.repos.MemberRepo;
import com.azad.bazaar.repos.RoleRepo;
import com.azad.bazaar.security.entities.MemberEntity;
import com.azad.bazaar.security.entities.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final MemberRepo memberRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public AuthService(MemberRepo memberRepo, RoleRepo roleRepo) {
        this.memberRepo = memberRepo;
        this.roleRepo = roleRepo;
    }

    public MemberDto registerMember(MemberDto memberDto) {

        String encodedPass = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPass);

        Set<String> roleNamesFromReq = memberDto.getRoleNames();

        if (roleNamesFromReq.contains("ADMIN")) {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            MemberEntity memberEntity = memberRepo.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("Member not found with username: " + username));

            boolean isAdmin = false;
            for (RoleEntity roleEntity: memberEntity.getRoles()) {
                if (roleEntity.getRoleName().equalsIgnoreCase("ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }

            if (!isAdmin) {
                throw new RuntimeException("Only admins can create a new Admin Member. '" + username + "' is not an Admin");
            }
        }

        Set<RoleEntity> rolesFromDb = roleNamesFromReq.stream()
                .map(roleName -> roleRepo.findByRoleName(roleName.toUpperCase()).orElseThrow(
                        () -> new RuntimeException("Role not found with name: " + roleName)))
                .collect(Collectors.toSet());

        MemberEntity memberEntity = modelMapper.map(memberDto, MemberEntity.class);
        memberEntity.setRoles(rolesFromDb);
        memberEntity.setEnabled(true);
        memberEntity.setExpired(false);
        memberEntity.setLocked(false);

        return modelMapper.map(memberRepo.save(memberEntity), MemberDto.class);
    }

    public void authenticateMember(String username, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken);
    }
}
