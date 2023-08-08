package com.azad.taskapiwithauth.services;

import com.azad.taskapiwithauth.auth.AppUserDto;
import com.azad.taskapiwithauth.auth.AppUserEntity;
import com.azad.taskapiwithauth.auth.LoginRequest;
import com.azad.taskapiwithauth.auth.RoleEntity;
import com.azad.taskapiwithauth.repositories.AppUserRepository;
import com.azad.taskapiwithauth.repositories.RoleRepository;
import com.azad.taskapiwithauth.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(AppUserRepository appUserRepository, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    public AppUserDto registerUser(AppUserDto dto) {

        if (dto.getRoleName() == null || dto.getRoleName().equalsIgnoreCase(""))
            dto.setRoleName("USER");
        else dto.setRoleName(dto.getRoleName().toUpperCase());

        String roleName = dto.getRoleName();
        if (roleName.equalsIgnoreCase("ADMIN")) {
            if (!loggedInUserIsAdmin())
                throw new RuntimeException("Only Admins can create a new Admin user");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        AppUserEntity member = modelMapper.map(dto, AppUserEntity.class);
        member.setRole(role);
        member.setEnabled(true);
        member.setExpired(false);
        member.setLocked(false);
        member.setCreatedAt(LocalDateTime.now());

        AppUserEntity savedMember = appUserRepository.save(member);

        return modelMapper.map(savedMember, AppUserDto.class);
    }

    public String authenticateUserAndGetEmail(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);

        return request.getEmail();
    }

    public AppUserDto updateUser(AppUserDto updatedDto) {
        AppUserEntity loggedInUser = getLoggedInUser();

        if (updatedDto.getFirstName() != null)
            loggedInUser.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            loggedInUser.setLastName(updatedDto.getLastName());
        loggedInUser.setModifiedAt(LocalDateTime.now());

        AppUserEntity updatedUser = appUserRepository.save(loggedInUser);

        return modelMapper.map(updatedUser, AppUserDto.class);
    }

    public void deleteUser() {
        AppUserEntity loggedInUser = getLoggedInUser();
        appUserRepository.delete(loggedInUser);
    }

    public void resetPassword(String updatedPassword) {
        AppUserEntity loggedInUser = getLoggedInUser();

        String encodedNewPass = passwordEncoder.encode(updatedPassword);
        loggedInUser.setPassword(encodedNewPass);

        appUserRepository.save(loggedInUser);
    }

    public boolean loggedInUserIsAdmin() {
        return getLoggedInUser().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }

    public AppUserEntity getLoggedInUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return securityUtils.getUserByEmail(email);
    }
}
