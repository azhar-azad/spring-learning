package com.azad.erecruitment.services.auth;

import com.azad.erecruitment.models.auth.LoginRequest;
import com.azad.erecruitment.models.auth.MemberDto;
import com.azad.erecruitment.models.auth.MemberEntity;
import com.azad.erecruitment.models.auth.RoleEntity;
import com.azad.erecruitment.repositories.MemberRepository;
import com.azad.erecruitment.repositories.RoleRepository;
import com.azad.erecruitment.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

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

    public MemberDto registerMember(MemberDto dto) {

        if (dto.getRoleName() == null || dto.getRoleName().isEmpty())
            dto.setRoleName("USER");
        else
            dto.setRoleName(dto.getRoleName().toUpperCase());

        String roleName = dto.getRoleName();
        if (roleName.equalsIgnoreCase("ADMIN")) {
            if (!loggedInUserIsAdmin())
                throw new RuntimeException("Only Admins can create a new Admin user");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        MemberEntity member = modelMapper.map(dto, MemberEntity.class);
        member.setRole(role);
        member.setEnabled(true);
        member.setExpired(false);
        member.setLocked(false);
        member.setMemberFrom(LocalDate.now());

        MemberEntity registeredMember = memberRepository.save(member);

        return modelMapper.map(registeredMember, MemberDto.class);

    }

    public String authenticateMemberAndGetEmail(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authToken);

        MemberEntity loggedInMember = memberRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RuntimeException("Member not found with email " + request.getEmail()));
        loggedInMember.setLastLoginAt(LocalDateTime.now());
        memberRepository.save(loggedInMember);

        return loggedInMember.getEmail();
    }

    public MemberDto updateMember(MemberDto updatedDto) {
        MemberEntity loggedInUser = getLoggedInMember();

        if (updatedDto.getFirstName() != null)
            loggedInUser.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            loggedInUser.setLastName(updatedDto.getLastName());
        if (updatedDto.getDesignation() != null)
            loggedInUser.setDesignation(updatedDto.getDesignation());

        MemberEntity updatedUser = memberRepository.save(loggedInUser);

        return modelMapper.map(updatedUser, MemberDto.class);
    }

    public void deleteMember() {
        MemberEntity loggedInUser = getLoggedInMember();
        memberRepository.delete(loggedInUser);
    }

    public void resetPassword(String updatedPassword) {
        MemberEntity loggedInUser = getLoggedInMember();

        String encodedPassword = passwordEncoder.encode(updatedPassword);
        loggedInUser.setPassword(encodedPassword);

        memberRepository.save(loggedInUser);
    }

    public boolean loggedInUserIsAdmin() {
        return getLoggedInMember().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }

    public MemberEntity getLoggedInMember() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return securityUtils.getUserByEmail(email);
    }
}
