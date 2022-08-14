package com.azad.jsonplaceholder.security.auth.api;

import com.azad.jsonplaceholder.models.dtos.MemberDto;
import com.azad.jsonplaceholder.models.responses.MemberResponse;
import com.azad.jsonplaceholder.rest.assemblers.MemberModelAssembler;
import com.azad.jsonplaceholder.security.auth.requests.LoginRequest;
import com.azad.jsonplaceholder.security.auth.requests.RegistrationRequest;
import com.azad.jsonplaceholder.security.jwt.JWTUtil;
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
@RequestMapping(path = "api/v1/auth")
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
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<EntityModel<MemberResponse>> getLoggedInUser() {

        MemberResponse memberResponse = modelMapper.map(authService.getLoggedInMember(), MemberResponse.class);

        return new ResponseEntity<>(memberModelAssembler.toModel(memberResponse), HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String username, HttpStatus statusToSend) {
        String token = jwtUtil.generateJwtToken(username);

        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
