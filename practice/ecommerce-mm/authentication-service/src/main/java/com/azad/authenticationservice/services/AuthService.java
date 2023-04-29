package com.azad.authenticationservice.services;

import com.azad.authenticationservice.models.dtos.AppUserDto;
import com.azad.authenticationservice.models.entities.AppUserEntity;
import com.azad.authenticationservice.models.entities.RoleEntity;
import com.azad.authenticationservice.models.pojos.AppUser;
import com.azad.authenticationservice.models.requests.LoginRequest;
import com.azad.authenticationservice.repositories.AppUserRepository;
import com.azad.authenticationservice.repositories.RoleRepository;
import com.azad.authenticationservice.security.SecurityUtils;
import com.azad.authenticationservice.security.jwt.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtils jwtUtils;

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

        String roleName = dto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) {
            if (!loggedInUserIsAdmin())
                throw new RuntimeException("Only admins can create a new Admin user");
        }

        String encodedPass = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPass);

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        AppUserEntity user = modelMapper.map(dto, AppUserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);
        user.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(appUserRepository.save(user), AppUserDto.class);
    }

    public String getAuthenticatedUserId(LoginRequest loginRequest) throws AuthenticationException {
        String uid = getUniqueIdentifier(modelMapper.map(loginRequest, AppUserDto.class));

        authenticateUser(uid, loginRequest.getPassword());

        return uid;
    }

    public void authenticateUser(String usernameOrEmail, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        authenticationManager.authenticate(authenticationToken);
    }

    public AppUserDto updateUser(AppUserDto updatedDto) {
        AppUserEntity loggedInUser = getLoggedInUser();

        if (updatedDto.getFirstName() != null)
            loggedInUser.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            loggedInUser.setLastName(updatedDto.getLastName());
        loggedInUser.setUpdatedAt(LocalDateTime.now());

        AppUserEntity updatedUser = appUserRepository.save(loggedInUser);
        return modelMapper.map(updatedUser, AppUserDto.class);
    }

    public <U extends AppUser> String getUniqueIdentifier(U u) {
        String uid = securityUtils.isUsernameBasedAuth() ? u.getUsername() : securityUtils.isEmailBasedAuth() ? u.getEmail() : null;
        if (uid == null)
            throw new RuntimeException("Unknown authentication base configured: " + securityUtils.getAuthBase());
        return uid;
    }

    public AppUserEntity getLoggedInUser() {
        String usernameOrEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return securityUtils.getUserByAuthBase(usernameOrEmail);
    }

    public boolean loggedInUserIsAdmin() {
        return getLoggedInUser().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }

    public ResponseEntity<Map<String, String>> generateTokenAndSend(String authenticatedUserId, HttpStatus statusToSend) {
        String token = jwtUtils.generateJwtToken(authenticatedUserId);
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), statusToSend);
    }
}
