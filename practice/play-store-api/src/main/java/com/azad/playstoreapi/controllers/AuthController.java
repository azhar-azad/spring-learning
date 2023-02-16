package com.azad.playstoreapi.controllers;

import com.azad.playstoreapi.assemblers.PlayStoreUserModelAssembler;
import com.azad.playstoreapi.models.dtos.PlayStoreUserDto;
import com.azad.playstoreapi.models.entities.PlayStoreUserEntity;
import com.azad.playstoreapi.models.requests.LoginRequest;
import com.azad.playstoreapi.models.requests.RegistrationRequest;
import com.azad.playstoreapi.models.responses.PlayStoreUserResponse;
import com.azad.playstoreapi.security.SecurityUtils;
import com.azad.playstoreapi.security.auth.AuthService;
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
    private PlayStoreUserModelAssembler assembler;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        PlayStoreUserDto userDto = modelMapper.map(request, PlayStoreUserDto.class);
        PlayStoreUserDto registeredUser = authService.registerUser(userDto);

        return authService.generateTokenAndSend(authService.getUniqueIdentifier(registeredUser), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        if (request.getUsername() == null && request.getEmail() == null)
            throw new RuntimeException("Invalid login request. No username or email to authenticate.");

        try {
            String authenticatedUserId = authService.getAuthenticatedUserId(request);

            return authService.generateTokenAndSend(authenticatedUserId, HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<PlayStoreUserResponse>> getUserInfo() {
        PlayStoreUserEntity loggedInUser = authService.getLoggedInUser();
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PlayStoreUserResponse response = modelMapper.map(loggedInUser, PlayStoreUserResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }
}
