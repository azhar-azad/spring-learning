package com.azad.moviepedia.controllers;

import com.azad.moviepedia.models.auth.LoginRequest;
import com.azad.moviepedia.models.auth.MemberDto;
import com.azad.moviepedia.models.auth.RegistrationRequest;
import com.azad.moviepedia.security.SecurityUtils;
import com.azad.moviepedia.services.auth.AuthService;
import jakarta.validation.Valid;
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
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleRegistration(@Valid @RequestBody RegistrationRequest request) {

        MemberDto dto = modelMapper.map(request, MemberDto.class);
        MemberDto registeredDto = authService.registerMember(dto);

        return generateTokenAndSend(registeredDto.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleLogin(@Valid @RequestBody LoginRequest request) {

        try {
            String authenticatedEmail = authService.authenticateMemberAndGetEmail(request);
            return generateTokenAndSend(authenticatedEmail, HttpStatus.ACCEPTED);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("AUTHENTICATION ERROR", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus httpStatus) {

        String token = securityUtils.generateJwtToken(email);
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), httpStatus);
    }
}
