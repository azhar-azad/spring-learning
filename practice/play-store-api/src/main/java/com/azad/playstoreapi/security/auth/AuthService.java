package com.azad.playstoreapi.security.auth;

import com.azad.playstoreapi.models.dtos.PlayStoreUserDto;
import com.azad.playstoreapi.models.entities.PlayStoreUserEntity;
import com.azad.playstoreapi.models.entities.RoleEntity;
import com.azad.playstoreapi.models.pojos.PlayStoreUser;
import com.azad.playstoreapi.models.requests.LoginRequest;
import com.azad.playstoreapi.repos.PlayStoreUserRepository;
import com.azad.playstoreapi.repos.RoleRepository;
import com.azad.playstoreapi.security.SecurityUtils;
import com.azad.playstoreapi.security.jwt.JwtUtil;
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

    private final PlayStoreUserRepository playStoreUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(PlayStoreUserRepository playStoreUserRepository, RoleRepository roleRepository) {
        this.playStoreUserRepository = playStoreUserRepository;
        this.roleRepository = roleRepository;
    }

    public PlayStoreUserDto registerUser(PlayStoreUserDto playStoreUserDto) throws RuntimeException {

        String roleName = playStoreUserDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // Someone is trying to create an Admin user.
            if (!loggedInUserIsAdmin()) {
                // But he/she is not an Admin
                throw new RuntimeException("Only admins can create a new Admin User");
            }
        }

        String encodedPass = passwordEncoder.encode(playStoreUserDto.getPassword());
        playStoreUserDto.setPassword(encodedPass);

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        PlayStoreUserEntity user = modelMapper.map(playStoreUserDto, PlayStoreUserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return modelMapper.map(playStoreUserRepository.save(user), PlayStoreUserDto.class);
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

    public PlayStoreUserEntity getLoggedInUser() {
        String usernameOrEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (securityUtils.isUsernameBasedAuth()) {
            return playStoreUserRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            return playStoreUserRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        else
            throw new RuntimeException("Unknown Authentication base configured. Valid authentication bases are USERNAME or EMAIL");
    }

    public boolean loggedInUserIsAdmin() {
        PlayStoreUserEntity loggedInUser = getLoggedInUser();
        String roleName = loggedInUser.getRole().getRoleName();
        return roleName.equalsIgnoreCase("ADMIN");
    }

    public <U extends PlayStoreUser> String getUniqueIdentifier(U u) {
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
