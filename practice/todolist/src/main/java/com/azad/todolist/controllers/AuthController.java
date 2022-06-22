package com.azad.todolist.controllers;

import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.requests.LoginRequest;
import com.azad.todolist.security.JWTUtil;
import com.azad.todolist.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTUtil jwtUtil;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Defining the function to handle the POST route for registering a user
    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> registerHandler(@RequestBody AppUser appUser) {

        AppUserDto appUserDto = modelMapper.map(appUser, AppUserDto.class);

        appUserDto = authService.getRegisteredAppUser(appUserDto);

        return generateTokenAndSend(appUserDto.getEmail(), HttpStatus.CREATED);
    }

    // Defining the function to handle the POST route for logging in a user
    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> loginHandler(@RequestBody LoginRequest loginRequest) {

        try {
            authService.authenticateAppUser(loginRequest.getEmail(), loginRequest.getPassword());

            // If this point is reached it means authentication was successful
            return generateTokenAndSend(loginRequest.getEmail(), HttpStatus.OK);

        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus statusToSend) {
        // Generate the JWT
        String token = jwtUtil.generateToken(email);

        // Respond with the JWT
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
