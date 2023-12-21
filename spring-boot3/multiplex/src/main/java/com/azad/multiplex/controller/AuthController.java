package com.azad.multiplex.controller;

import com.azad.multiplex.model.dto.LoginRequest;
import com.azad.multiplex.model.dto.MemberDto;
import com.azad.multiplex.model.dto.MemberResponse;
import com.azad.multiplex.model.dto.RegistrationRequest;
import com.azad.multiplex.model.entity.MemberEntity;
import com.azad.multiplex.security.SecurityUtils;
import com.azad.multiplex.service.AuthService;
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

        String token = authService.generateAndGetJwtToken(registeredDto.getEmail());
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleLogin(@Valid @RequestBody LoginRequest request) {

        try {
            String authenticatedEmail = authService.authenticateMemberAndGetEmail(request);
            return new ResponseEntity<>(
                    Collections.singletonMap("TOKEN", authService.generateAndGetJwtToken(authenticatedEmail)),
                    HttpStatus.ACCEPTED);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("AUTHENTICATION ERROR", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<MemberResponse> handleMemberGet() {

        MemberEntity loggedInMember;
        try {
            loggedInMember = authService.getLoggedInMember();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        MemberResponse response = modelMapper.map(loggedInMember, MemberResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> handleMemberDelete() {

        try {
            authService.deleteMember();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Member deleted");
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
}
