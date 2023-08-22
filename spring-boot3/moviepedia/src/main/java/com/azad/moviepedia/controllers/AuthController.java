package com.azad.moviepedia.controllers;

import com.azad.moviepedia.models.auth.*;
import com.azad.moviepedia.security.SecurityUtils;
import com.azad.moviepedia.services.auth.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

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
        MemberDto registeredDto;
        try {
            registeredDto = authService.registerMember(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

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
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<MemberResponse> handleMemberGet() {

        MemberEntity loggedInUser;
        try {
            loggedInUser = authService.getLoggedInMember();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        MemberResponse response = modelMapper.map(loggedInUser, MemberResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MemberResponse> handleMemberUpdate(@RequestBody MemberDto updatedDto) {

        MemberDto savedDto;
        try {
            savedDto = authService.updateMember(updatedDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(modelMapper.map(savedDto, MemberResponse.class), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> handleMemberDelete() {
        try {
            authService.deleteMember();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Member Deleted");
    }

    @PutMapping(path = "/resetPassword")
    public ResponseEntity<?> handleResetPassword(@RequestBody RegistrationRequest request) {
        try {
            authService.resetPassword(request.getPassword());
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.accepted().build();
    }

    /*
        Private Methods
     */
    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus httpStatus) {

        String token = securityUtils.generateJwtToken(email);
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), httpStatus);
    }
}
