package com.azad.online_shop.controller;

import com.azad.online_shop.exception.UnauthorizedAccessException;
import com.azad.online_shop.model.dto.users.LoginRequest;
import com.azad.online_shop.model.dto.users.RegistrationRequest;
import com.azad.online_shop.model.dto.users.UserDto;
import com.azad.online_shop.model.dto.users.UserResponse;
import com.azad.online_shop.model.entity.UserEntity;
import com.azad.online_shop.security.SecurityUtils;
import com.azad.online_shop.service.AuthService;
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
public class AuthController {

    private ModelMapper modelMapper;
    private SecurityUtils securityUtils;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleRegistration(@Valid @RequestBody RegistrationRequest request) {

        UserDto dto = modelMapper.map(request, UserDto.class);
        UserDto registeredDto;
        try {
            registeredDto = authService.registerUser(dto);
        } catch (UnauthorizedAccessException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String token = authService.generateAndGetJwtToken(registeredDto.getEmail());
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleLogin(@Valid @RequestBody LoginRequest request) {

        try {
            String authenticatedEmail = authService.authenticateUserAndGetEmail(request);
            return new ResponseEntity<>(
                    Collections.singletonMap("TOKEN", authService.generateAndGetJwtToken(authenticatedEmail)),
                    HttpStatus.ACCEPTED);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<UserResponse> handleUserGet() {

        UserEntity loggedInUser;
        try {
            loggedInUser = authService.getLoggedInUser();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return new ResponseEntity<>(modelMapper.map(loggedInUser, UserResponse.class),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> handleUserDelete() {

        try {
            authService.deleteUser();
        } catch (UnauthorizedAccessException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok("User deleted");
    }

    @PutMapping(path = "/resetPassword")
    public ResponseEntity<?> handleResetPassword(@RequestBody RegistrationRequest request) {

        try {
            authService.resetPassword(request.getPassword());
        } catch (UnauthorizedAccessException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.accepted().build();
    }
}
