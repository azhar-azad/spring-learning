package com.azad.taskapiwithauth.resources;

import com.azad.taskapiwithauth.models.auth.AppUserDto;
import com.azad.taskapiwithauth.models.auth.AppUserResponse;
import com.azad.taskapiwithauth.models.auth.LoginRequest;
import com.azad.taskapiwithauth.models.auth.RegistrationRequest;
import com.azad.taskapiwithauth.security.SecurityUtils;
import com.azad.taskapiwithauth.services.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthRestResource {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;

    @Autowired
    public AuthRestResource(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        AppUserDto dto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerUser(dto);

        return generateTokenAndSend(registeredUser.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        try {
            String authenticatedEmail = authService.authenticateUserAndGetEmail(request);
            return generateTokenAndSend(authenticatedEmail, HttpStatus.ACCEPTED);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<AppUserResponse> getUser() {
        AppUserResponse response = modelMapper.map(authService.getLoggedInUser(), AppUserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AppUserResponse> handleUserUpdate(@RequestBody AppUserDto updatedDto) {
        AppUserDto savedDto = authService.updateUser(updatedDto);
        return new ResponseEntity<>(modelMapper.map(savedDto, AppUserResponse.class), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> handleUserUnRegistration() {
        authService.deleteUser();
        return ResponseEntity.ok("User Deleted");
    }

    @PutMapping(path = "/resetPassword")
    public ResponseEntity<?> handleResetPassword(@RequestBody RegistrationRequest request) {
        authService.resetPassword(request.getPassword());
        return ResponseEntity.accepted().build();
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String authenticatedUserId, HttpStatus statusToSend) {
        String token = securityUtils.generateJwtToken(authenticatedUserId);
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), statusToSend);
    }
}
