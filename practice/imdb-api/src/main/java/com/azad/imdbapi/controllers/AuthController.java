package com.azad.imdbapi.controllers;

import com.azad.imdbapi.assemblers.ImdbUserModelAssembler;
import com.azad.imdbapi.dtos.ImdbUserDto;
import com.azad.imdbapi.requests.LoginRequest;
import com.azad.imdbapi.requests.RegistrationRequest;
import com.azad.imdbapi.responses.ImdbUserResponse;
import com.azad.imdbapi.security.SecurityUtils;
import com.azad.imdbapi.security.auth.AuthService;
import com.azad.imdbapi.security.jwt.JWTUtil;
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
    private final ImdbUserModelAssembler assembler;

    @Autowired
    public AuthController(AuthService authService, ImdbUserModelAssembler assembler) {
        this.authService = authService;
        this.assembler = assembler;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        ImdbUserDto imdbUserDto = modelMapper.map(request, ImdbUserDto.class);

        ImdbUserDto registeredUser = authService.registerUser(imdbUserDto);

        return securityUtils.generateTokenAndSend(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        if (request.getUsername() == null && request.getEmail() == null)
            throw new RuntimeException("Invalid login request. No username or email to authenticate.");

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
    public ResponseEntity<EntityModel<ImdbUserResponse>> getLoggedInUser() {
        ImdbUserResponse response = modelMapper.map(authService.getLoggedInUser(), ImdbUserResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }
}
