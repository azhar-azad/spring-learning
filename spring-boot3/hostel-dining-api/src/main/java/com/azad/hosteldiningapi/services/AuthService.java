package com.azad.hosteldiningapi.services;

import com.azad.hosteldiningapi.models.auth.LoginRequest;
import com.azad.hosteldiningapi.models.auth.MemberDto;
import com.azad.hosteldiningapi.models.auth.MemberEntity;
import com.azad.hosteldiningapi.models.auth.RoleEntity;
import com.azad.hosteldiningapi.models.memberinfo.MemberInfo;
import com.azad.hosteldiningapi.models.memberinfo.MemberInfoEntity;
import com.azad.hosteldiningapi.repositories.MemberInfoRepository;
import com.azad.hosteldiningapi.repositories.MemberRepository;
import com.azad.hosteldiningapi.repositories.RoleRepository;
import com.azad.hosteldiningapi.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(MemberRepository memberRepository, RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    public MemberDto registerMember(MemberDto dto) {

        if (dto.getRoleName() == null || dto.getRoleName().equalsIgnoreCase(""))
            dto.setRoleName("STUDENT");
        else dto.setRoleName(dto.getRoleName().toUpperCase());

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
        member.setCreatedAt(LocalDateTime.now());
        member.setMemberInfo(null);

        MemberEntity savedMember = memberRepository.save(member);

        MemberDto savedMemberDto = modelMapper.map(savedMember, MemberDto.class);

        if (dto.getMemberInfo() != null) {
            MemberInfoEntity memberInfo = modelMapper.map(dto.getMemberInfo(), MemberInfoEntity.class);
            memberInfo.setTotalTokenReceived(0L);
            memberInfo.setTotalExpenses(0.0);
            memberInfo.setCreatedAt(LocalDateTime.now());
            memberInfo.setMember(savedMember);
            MemberInfoEntity savedMemberInfo = memberInfoRepository.save(memberInfo);
            savedMemberDto.setMemberInfo(modelMapper.map(savedMemberInfo, MemberInfo.class));
        }

        return savedMemberDto;
    }

    public String authenticateMemberAndGetEmail(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);

        return request.getEmail();
    }

    public MemberDto updateMember(MemberDto updatedDto) {
        MemberEntity loggedInUser = getLoggedInUser();

        if (updatedDto.getFirstName() != null)
            loggedInUser.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            loggedInUser.setLastName(updatedDto.getLastName());
        loggedInUser.setModifiedAt(LocalDateTime.now());

        MemberEntity updatedUser = memberRepository.save(loggedInUser);
        MemberDto updatedMemberDto = modelMapper.map(updatedUser, MemberDto.class);

        if (updatedDto.getMemberInfo() != null) {
            MemberInfoEntity memberInfo = memberInfoRepository.findById(loggedInUser.getMemberInfo().getId())
                    .orElseThrow(() -> new RuntimeException("MemberInfo not found for id: " + loggedInUser.getMemberInfo().getId()));

            MemberInfo dtoMemberInfo = updatedDto.getMemberInfo();
            if (dtoMemberInfo.getRollNo() != null)
                memberInfo.setRollNo(dtoMemberInfo.getRollNo());
            if (dtoMemberInfo.getSeatNo() != null)
                memberInfo.setSeatNo(dtoMemberInfo.getSeatNo());
            if (dtoMemberInfo.getRoomNo() != null)
                memberInfo.setRoomNo(dtoMemberInfo.getRoomNo());
            if (dtoMemberInfo.getSession() != null)
                memberInfo.setRoomNo(dtoMemberInfo.getRoomNo());
            if (dtoMemberInfo.getDepartment() != null)
                memberInfo.setDepartment(dtoMemberInfo.getDepartment());

            MemberInfoEntity updatedMemberInfo = memberInfoRepository.save(memberInfo);
            updatedMemberDto.setMemberInfo(modelMapper.map(updatedMemberInfo, MemberInfo.class));
        }

        return updatedMemberDto;
    }

    public void deleteMember() {
        MemberEntity loggedInUser = getLoggedInUser();
        memberRepository.delete(loggedInUser);
    }

    public void resetPassword(String updatedPassword) {
        MemberEntity loggedInUser = getLoggedInUser();

        String encodedNewPass = passwordEncoder.encode(updatedPassword);
        loggedInUser.setPassword(encodedNewPass);

        memberRepository.save(loggedInUser);
    }

    public boolean loggedInUserIsAdmin() {
        return getLoggedInUser().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }

    public MemberEntity getLoggedInUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return securityUtils.getUserByEmail(email);
    }

    public boolean isUserAuthorized(String userRole, Set<String> authorizedRoles) {
        return authorizedRoles.contains(userRole);
    }

    public boolean isUserAuthorized(String userRole, String... authorizedRoles) {
        for (String authorizedRole: authorizedRoles) {
            if (userRole.equalsIgnoreCase(authorizedRole))
                return true;
        }
        return false;
    }
}
