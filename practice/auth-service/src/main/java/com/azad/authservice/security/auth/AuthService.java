package com.azad.authservice.security.auth;

import com.azad.authservice.models.dtos.AppUserDto;
import com.azad.authservice.models.entities.AppUserEntity;
import com.azad.authservice.models.entities.RoleEntity;
import com.azad.authservice.models.pojos.AppUser;
import com.azad.authservice.models.requests.LoginRequest;
import com.azad.authservice.repos.AppUserRepository;
import com.azad.authservice.repos.RoleRepository;
import com.azad.authservice.security.SecurityUtils;
import com.azad.authservice.security.jwt.JwtUtil;
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

import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

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

    public AppUserDto registerUser(AppUserDto appUserDto) throws RuntimeException {

        String roleName = appUserDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // Someone is trying to create an Admin user.
            if (!loggedInUserIsAdmin()) {
                // But he/she is not an Admin
                throw new RuntimeException("Only admins can create a new Admin User");
            }
        }

        String encodedPass = passwordEncoder.encode(appUserDto.getPassword());
        appUserDto.setPassword(encodedPass);

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        AppUserEntity user = modelMapper.map(appUserDto, AppUserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return modelMapper.map(appUserRepository.save(user), AppUserDto.class);
    }

    public String getAuthenticatedUserId(LoginRequest request) {

        if (securityUtils.isUsernameBasedAuth()) {
            authenticateUser(request.getUsername(), request.getPassword());
            return request.getUsername();
        } else if (securityUtils.isEmailBasedAuth()) {
            authenticateUser(request.getEmail(), request.getPassword());
            return request.getEmail();
        }
        throw new RuntimeException("Unknown Authentication base configured. Valid authentication bases are USERNAME or EMAIL");
    }

    public void authenticateUser(String usernameOrEmail, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password);

        authenticationManager.authenticate(authenticationToken);
    }

    public AppUserEntity getLoggedInUser() {
        String usernameOrEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (securityUtils.isUsernameBasedAuth()) {
            return appUserRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            return appUserRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        else
            throw new RuntimeException("Unknown Authentication base configured. Valid authentication bases are USERNAME or EMAIL");
    }

    public boolean loggedInUserIsAdmin() {
        AppUserEntity loggedInUser = getLoggedInUser();
        String roleName = loggedInUser.getRole().getRoleName();
        return roleName.equalsIgnoreCase("ADMIN");
    }

    public <U extends AppUser> String getUniqueIdentifier(U u) {
        String uid = securityUtils.isUsernameBasedAuth() ? u.getUsername() : securityUtils.isEmailBasedAuth() ? u.getEmail() : null;
        if (uid == null)
            throw new RuntimeException("Unknown Authentication base configured: " + securityUtils.getAuthBase());
        return uid;
    }

    public ResponseEntity<Map<String, String>> generateTokenAndSend(String authenticatedUserId, HttpStatus statusToSend) {
        String token = jwtUtil.generateJwtToken(authenticatedUserId);
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }

//    public String getUniqueUserIdentifier(AppUserEntity user) {
//        String uid = securityUtils.isUsernameBasedAuth() ? user.getUsername() : securityUtils.isEmailBasedAuth() ? user.getEmail() : null;
//        if (uid == null)
//            throw new RuntimeException("Unknown Authentication base configured: " + securityUtils.getAuthBase());
//        return uid;
//    }

}
