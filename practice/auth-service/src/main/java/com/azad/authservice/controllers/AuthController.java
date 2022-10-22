package com.azad.authservice.controllers;

import com.azad.authservice.assemblers.AppUserModelAssembler;
import com.azad.authservice.models.dtos.AppUserDto;
import com.azad.authservice.models.requests.LoginRequest;
import com.azad.authservice.models.requests.RegistrationRequest;
import com.azad.authservice.models.responses.AppUserResponse;
import com.azad.authservice.security.SecurityUtils;
import com.azad.authservice.security.auth.AuthService;
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
    private AppUserModelAssembler assembler;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        AppUserDto userDto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerUser(userDto);

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
    public ResponseEntity<EntityModel<AppUserResponse>> getUserInfo() {
        AppUserResponse response = modelMapper.map(authService.getLoggedInUser(), AppUserResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }
}
