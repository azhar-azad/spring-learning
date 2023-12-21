package com.azad.multiplex.service;

import com.azad.multiplex.model.constant.RoleType;
import com.azad.multiplex.model.dto.LoginRequest;
import com.azad.multiplex.model.dto.MemberDto;
import com.azad.multiplex.model.entity.MemberEntity;
import com.azad.multiplex.model.entity.RoleEntity;
import com.azad.multiplex.repository.MemberRepository;
import com.azad.multiplex.repository.RoleRepository;
import com.azad.multiplex.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Supplier;

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

        if (dto.getRoleName() == null || dto.getRoleName().name().isEmpty())
            dto.setRoleName(RoleType.CUSTOMER);

        if (dto.getRoleName().equals(RoleType.ADMIN)) {
            if (!loggedInMemberIsAdmin())
                throw new RuntimeException("Only admins can create a new admin user");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        RoleEntity role = roleRepository.findByRoleName(dto.getRoleName().name())
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + dto.getRoleName()));

        MemberEntity member = modelMapper.map(dto, MemberEntity.class);
        member.setRole(role);
        member.setEnabled(true);
        member.setLocked(false);
        member.setExpired(false);
        member.setMemberFrom(LocalDate.now());

        MemberEntity registeredMember = memberRepository.save(member);

        return modelMapper.map(registeredMember, MemberDto.class);
    }

    public String authenticateMemberAndGetEmail(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authToken);

        MemberEntity loggedInMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with email " + request.getEmail()));
        loggedInMember.setLastLoginAt(LocalDateTime.now());
        memberRepository.save(loggedInMember);

        return loggedInMember.getEmail();
    }

    public void deleteMember() {
        MemberEntity loggedInMember = getLoggedInMember();
        memberRepository.delete(loggedInMember);
    }

    public void resetPassword(String updatedPassword) {
        MemberEntity loggedInMember = getLoggedInMember();
        String encodedPass = passwordEncoder.encode(updatedPassword);
        loggedInMember.setPassword(encodedPass);

        memberRepository.save(loggedInMember);
    }

    public MemberEntity getLoggedInMember() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityUtils.getMemberByEmail(email);
    }

    public String generateAndGetJwtToken(String email) {
        return securityUtils.generateJwtToken(email);
    }

    public void authInit() {
        BiFunction<RoleType, Integer, RoleEntity> roleCreator = (roleName, id) -> {
            RoleEntity role = new RoleEntity();
            role.setId(id);
            role.setRoleName(roleName.name());
            return roleRepository.save(role);
        };

        RoleEntity adminRole = roleRepository.findByRoleName(RoleType.ADMIN.name()).orElse(roleCreator.apply(RoleType.ADMIN, 1));
        roleRepository.findByRoleName(RoleType.CUSTOMER.name()).orElse(roleCreator.apply(RoleType.CUSTOMER, 2));

        Supplier<MemberEntity> superAdminCreator = () -> {
            MemberEntity admin = new MemberEntity();
            admin.setEmail("superadmin@gmail.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setMobile("0123456");
            admin.setRole(adminRole);
            admin.setMemberFrom(LocalDate.now());
            admin.setEnabled(true);
            return memberRepository.save(admin);
        };

        memberRepository.findByEmail("superadmin@gmail.com").orElseGet(superAdminCreator);
    }

    private boolean loggedInMemberIsAdmin() {
        return getLoggedInMember().getRole().getRoleName().equals(RoleType.ADMIN.name());
    }
}
