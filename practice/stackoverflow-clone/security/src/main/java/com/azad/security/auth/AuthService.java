package com.azad.security.auth;

import com.azad.data.models.dtos.AppUserDto;
import com.azad.data.models.entities.AppUserEntity;
import com.azad.data.models.entities.RoleEntity;
import com.azad.data.models.pojos.AppUser;
import com.azad.data.models.requests.LoginRequest;
import com.azad.data.repos.AppUserRepository;
import com.azad.data.repos.RoleRepository;
import com.azad.security.SecurityUtils;
import com.azad.security.jwt.JwtUtil;
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
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(AppUserRepository appUserRepository, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    public AppUserDto registerAppUser(AppUserDto appUserDto) throws RuntimeException {

        if (appUserDto.getRoleName() == null || appUserDto.getRoleName().equalsIgnoreCase(""))
            appUserDto.setRoleName("USER");

        String roleName = appUserDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) {
            if (!loggedInUserIsAdmin())
                throw new RuntimeException("Only Admins can create a new Admin user");
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

    public AppUserDto updateUser(AppUserDto updatedDto) {

        AppUserEntity loggedInUser = getLoggedInUser();

        if (updatedDto.getFirstName() != null)
            loggedInUser.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getLastName() != null)
            loggedInUser.setLastName(updatedDto.getLastName());
        if (updatedDto.getJobTitle() != null)
            loggedInUser.setJobTitle(updatedDto.getJobTitle());
        if (updatedDto.getCompanyName() != null)
            loggedInUser.setCompanyName(updatedDto.getCompanyName());
        if (updatedDto.getLocation() != null)
            loggedInUser.setLocation(updatedDto.getLocation());

        AppUserEntity updatedUser = appUserRepository.save(loggedInUser);

        return modelMapper.map(updatedUser, AppUserDto.class);
    }

    public <U extends AppUser> String getUniqueIdentifier(U u) {
        String uid = securityUtils.isUsernameBasedAuth()
                ? u.getUsername() : securityUtils.isEmailBasedAuth() ? u.getEmail() : null;
        if (uid == null)
            throw new RuntimeException("Unknown Authentication base configured: " + securityUtils.getAuthBase());
        return uid;
    }

    public AppUserEntity getLoggedInUser() {
        String usernameOrEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (securityUtils.isUsernameBasedAuth())
            return appUserRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        else if (securityUtils.isEmailBasedAuth())
            return appUserRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        else
            throw new RuntimeException("Unknown Authentication base configured. Valid authentication bases are USERNAME or EMAIL");
    }

    public boolean loggedInUserIsAdmin() {
        return getLoggedInUser().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }

    public ResponseEntity<Map<String, String>> generateTokenAndSend(String authenticatedUserId, HttpStatus statusToSend) {
        String token = jwtUtil.generateJwtToken(authenticatedUserId);
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
