package com.azad.userauthservice.controllers;

import com.azad.userauthservice.assemblers.AppUserModelAssembler;
import com.azad.userauthservice.models.dtos.AppUserDto;
import com.azad.userauthservice.models.entities.AppUserEntity;
import com.azad.userauthservice.models.requests.LoginRequest;
import com.azad.userauthservice.models.requests.RegistrationRequest;
import com.azad.userauthservice.models.responses.AppUserResponse;
import com.azad.userauthservice.security.SecurityUtils;
import com.azad.userauthservice.security.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;
    private final AppUserModelAssembler assembler;

    @Autowired
    public AuthController(AuthService authService, AppUserModelAssembler assembler) {
        this.authService = authService;
        this.assembler = assembler;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        AppUserDto appUserDto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerUser(appUserDto);

        return securityUtils.generateTokenAndSend(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        if (request.getUsername() == null && request.getEmail() == null)
            throw new RuntimeException("Invalid login request. No username or email to authenticate");

        try {
            if (securityUtils.isUsernameBasedAuth()) {
                authService.authenticateUser(request.getUsername(), request.getPassword());
                return securityUtils.generateTokenAndSend(request.getUsername(), HttpStatus.OK);
            }
            else if (securityUtils.isEmailBasedAuth()) {
                authService.authenticateUser(request.getEmail(), request.getPassword());
                return securityUtils.generateTokenAndSend(request.getEmail(), HttpStatus.OK);
            }
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<AppUserResponse>> getLoggedInUser() {
        AppUserEntity loggedInUser = authService.getLoggedInUser();
        AppUserResponse response = modelMapper.map(loggedInUser, AppUserResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }
}
