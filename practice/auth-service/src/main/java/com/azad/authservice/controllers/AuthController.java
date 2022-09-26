package com.azad.authservice.controllers;

import com.azad.authservice.assemblers.AppUserModelAssembler;
import com.azad.authservice.models.dtos.AppUserDto;
import com.azad.authservice.models.requests.LoginRequest;
import com.azad.authservice.models.requests.RegistrationRequest;
import com.azad.authservice.models.responses.AppUserResponse;
import com.azad.authservice.security.auth.AuthService;
import com.azad.authservice.security.jwt.JwtUtil;
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
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUserModelAssembler assembler;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        AppUserDto userDto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerUser(userDto);

        return generateTokenAndSend(registeredUser.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {
        try {
            authService.authenticateUser(request.getEmail(), request.getPassword());

            return generateTokenAndSend(request.getEmail(), HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<AppUserResponse>> getUserInfo() {
        AppUserResponse response = modelMapper.map(authService.getLoggedInUser(), AppUserResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus statusToSend) {
        String token = jwtUtil.generateJwtToken(email);
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
