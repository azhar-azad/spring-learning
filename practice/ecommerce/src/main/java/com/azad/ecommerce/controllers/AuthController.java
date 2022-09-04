package com.azad.ecommerce.controllers;

import com.azad.ecommerce.assemblers.UserModelAssembler;
import com.azad.ecommerce.models.dtos.UserDto;
import com.azad.ecommerce.models.requests.LoginRequest;
import com.azad.ecommerce.models.requests.RegistrationRequest;
import com.azad.ecommerce.models.responses.UserResponse;
import com.azad.ecommerce.security.SecurityUtils;
import com.azad.ecommerce.security.auth.AuthService;
import com.azad.ecommerce.security.jwt.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private SecurityUtils securityUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserModelAssembler assembler;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        UserDto userDto = modelMapper.map(request, UserDto.class);

        UserDto registeredUser = authService.registerUser(userDto);

        if (securityUtils.isUsernameBasedAuth())
            return generateTokenAndSend(registeredUser.getUsername(), HttpStatus.CREATED);
        else if (securityUtils.isEmailBasedAuth())
            return generateTokenAndSend(registeredUser.getEmail(), HttpStatus.CREATED);
        throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        if (request.getUsername() == null && request.getEmail() == null)
            throw new RuntimeException("Invalid login request. No username or email to authenticate.");

        try {
            if (securityUtils.isUsernameBasedAuth())
                authService.authenticateUser(request.getUsername(), request.getPassword());
            else if (securityUtils.isEmailBasedAuth())
                authService.authenticateUser(request.getEmail(), request.getPassword());
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/met")
    public ResponseEntity<EntityModel<UserResponse>> getLoggedInUser() {
        UserResponse userResponse = modelMapper.map(authService.getLoggedInUser(), UserResponse.class);

        return new ResponseEntity<>(assembler.toModel(userResponse), HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String usernameOrEmail, HttpStatus statusToReturn) {
        String token = jwtUtil.generateJwtToken(usernameOrEmail);
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToReturn);
    }
}
