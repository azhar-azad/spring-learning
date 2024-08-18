package com.azad.moviepedia.services.auth;

import com.azad.moviepedia.models.auth.LoginRequest;
import com.azad.moviepedia.models.auth.MemberDto;
import com.azad.moviepedia.models.auth.MemberEntity;
import com.azad.moviepedia.models.auth.RoleEntity;
import com.azad.moviepedia.models.constants.RoleType;
import com.azad.moviepedia.repositories.MemberRepository;
import com.azad.moviepedia.repositories.RoleRepository;
import com.azad.moviepedia.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Service
public class AuthService {

    @Value("${test_admin_id}")
    private String testAdminId;

    @Value("${test_admin_pass}")
    private String testAdminPass;

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
            dto.setRoleName(RoleType.USER.name());
        else
            dto.setRoleName(dto.getRoleName().toUpperCase());

        String roleName = dto.getRoleName();
        if (roleName.equalsIgnoreCase(RoleType.ADMIN.name())) {
            if (!loggedInUserIsAdmin())
                throw new RuntimeException("Only Admins can create a new Admin user");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        MemberEntity member = modelMapper.map(dto, MemberEntity.class);
        member.setRole(role);
        member.setTotalReviews(0);
        member.setTotalRatings(0);
        member.setAvgRatingGiven(0.0);
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
        if (updatedDto.getOccupation() != null)
            loggedInUser.setOccupation(updatedDto.getOccupation());

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

    public void authInit() {
        BiFunction<RoleType, Long, RoleEntity> roleCreator = (roleName, id) -> {
            RoleEntity role = new RoleEntity();
            role.setId(id);
            role.setRoleName(roleName.name());
            return roleRepository.save(role);
        };

        RoleEntity adminRole = roleRepository.findByRoleName(RoleType.ADMIN.name())
                .orElse(roleCreator.apply(RoleType.ADMIN, 1L));
        roleRepository.findByRoleName(RoleType.USER.name())
                .orElse(roleCreator.apply(RoleType.USER, 2L));

        Supplier<MemberEntity> testAdminCreator = () -> {
            MemberEntity admin = new MemberEntity();
            admin.setEmail(testAdminId);
            admin.setPassword(passwordEncoder.encode(testAdminPass));
            admin.setRole(adminRole);
            admin.setMemberFrom(LocalDate.now());
            admin.setFirstName("Test Admin");
            admin.setLastName("admin1");
            admin.setTotalReviews(0);
            admin.setTotalRatings(0);
            admin.setAvgRatingGiven(0.0);
            admin.setEnabled(true);
            return memberRepository.save(admin);
        };

        memberRepository.findByEmail(testAdminId).orElseGet(testAdminCreator);
    }
}
