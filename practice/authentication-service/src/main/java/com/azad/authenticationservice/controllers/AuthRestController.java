package com.azad.authenticationservice.controllers;

import com.azad.authenticationservice.assemblers.AppUserModelAssembler;
import com.azad.authenticationservice.models.dtos.AppUserDto;
import com.azad.authenticationservice.models.requests.LoginRequest;
import com.azad.authenticationservice.models.requests.RegistrationRequest;
import com.azad.authenticationservice.models.responses.AppUserResponse;
import com.azad.authenticationservice.security.SecurityUtils;
import com.azad.authenticationservice.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.AuthenticationException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;
    private final AppUserModelAssembler assembler;

    @Autowired
    public AuthRestController(AuthService authService, AppUserModelAssembler assembler) {
        this.authService = authService;
        this.assembler = assembler;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        AppUserDto dto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerUser(dto);

        return authService.generateTokenAndSend(authService.getUniqueIdentifier(registeredUser), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        if (request.getUsername() == null && request.getEmail() == null)
            throw new RuntimeException("Invalid Login request. No username or email to authenticate.");

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

    @PutMapping(path = "/me")
    public ResponseEntity<EntityModel<AppUserResponse>> updateUserInfo(@RequestBody AppUserDto updatedDto) {

        AppUserDto savedDto = authService.updateUser(updatedDto);

        EntityModel<AppUserResponse> responseEntityModel = assembler.toModel(modelMapper.map(savedDto, AppUserResponse.class));

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }
}
