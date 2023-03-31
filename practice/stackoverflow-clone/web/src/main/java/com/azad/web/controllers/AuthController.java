package com.azad.web.controllers;

import com.azad.data.models.dtos.AppUserDto;
import com.azad.data.models.requests.LoginRequest;
import com.azad.data.models.requests.RegistrationRequest;
import com.azad.data.models.responses.AppUserResponse;
import com.azad.security.SecurityUtils;
import com.azad.security.auth.AuthService;
import com.azad.web.assemblers.AppUserModelAssembler;
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

        AppUserDto appUserDto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerAppUser(appUserDto);

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

    @PutMapping(path = "/me")
    public ResponseEntity<EntityModel<AppUserResponse>> updateUserInfo(@RequestBody AppUserDto updatedDto) {

        AppUserDto savedDto = authService.updateUser(updatedDto);

        EntityModel<AppUserResponse> responseEntityModel = assembler.toModel(modelMapper.map(savedDto, AppUserResponse.class));

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }
}
