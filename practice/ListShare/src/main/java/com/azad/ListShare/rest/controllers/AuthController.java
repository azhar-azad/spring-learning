package com.azad.ListShare.rest.controllers;

import com.azad.ListShare.models.dtos.MemberDto;
import com.azad.ListShare.models.requests.LoginRequest;
import com.azad.ListShare.models.requests.RegistrationRequest;
import com.azad.ListShare.models.responses.MemberResponse;
import com.azad.ListShare.rest.assemblers.MemberModelAssembler;
import com.azad.ListShare.security.auth.AuthService;
import com.azad.ListShare.security.jwt.JWTUtil;
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
    private ModelMapper modelMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MemberModelAssembler memberModelAssembler;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<MemberResponse>> getLoggedInUser() {

        MemberResponse memberResponse = modelMapper.map(authService.getLoggedInMember(), MemberResponse.class);

        return new ResponseEntity<>(memberModelAssembler.toModel(memberResponse), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleMemberRegistration(@Valid @RequestBody RegistrationRequest request) {

        MemberDto memberDto = modelMapper.map(request, MemberDto.class);

        MemberDto registeredMember = authService.registerMember(memberDto);

        return generateTokenAndSend(registeredMember.getUsername(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleMemberLogin(@Valid @RequestBody LoginRequest request) {

        try {
            authService.authenticateMember(request.getUsername(), request.getPassword());

            return generateTokenAndSend(request.getUsername(), HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String username, HttpStatus statusToReturn) {
        String token = jwtUtil.generateJwtToken(username);

        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToReturn);
    }
}
