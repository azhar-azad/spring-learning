package com.azad.moviepedia.services.auth;

import com.azad.moviepedia.models.auth.LoginRequest;
import com.azad.moviepedia.models.auth.MemberDto;
import com.azad.moviepedia.models.auth.MemberEntity;
import com.azad.moviepedia.models.auth.RoleEntity;
import com.azad.moviepedia.repositories.MemberRepository;
import com.azad.moviepedia.repositories.RoleRepository;
import com.azad.moviepedia.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

        return request.getEmail();
    }

    public boolean loggedInUserIsAdmin() {
        return getLoggedInUser().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }

    public MemberEntity getLoggedInUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return securityUtils.getUserByEmail(email);
    }
}
