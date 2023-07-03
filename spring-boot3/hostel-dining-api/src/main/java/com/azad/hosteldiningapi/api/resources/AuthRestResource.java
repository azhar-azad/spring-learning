package com.azad.hosteldiningapi.api.resources;

import com.azad.hosteldiningapi.api.assembers.MemberResponseModelAssembler;
import com.azad.hosteldiningapi.models.auth.LoginRequest;
import com.azad.hosteldiningapi.models.auth.MemberDto;
import com.azad.hosteldiningapi.models.auth.MemberResponse;
import com.azad.hosteldiningapi.models.auth.RegistrationRequest;
import com.azad.hosteldiningapi.security.SecurityUtils;
import com.azad.hosteldiningapi.services.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthRestResource {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    private final AuthService authService;
    private final MemberResponseModelAssembler assembler;

    @Autowired
    public AuthRestResource(AuthService authService, MemberResponseModelAssembler assembler) {
        this.authService = authService;
        this.assembler = assembler;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        MemberDto dto = modelMapper.map(request, MemberDto.class);
        MemberDto registeredUser = authService.registerMember(dto);

        return generateTokenAndSend(registeredUser.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        try {
            String authenticatedUsernameOrEmail = authService.authenticateMemberAndGetEmail(request);
            return generateTokenAndSend(authenticatedUsernameOrEmail, HttpStatus.ACCEPTED);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<EntityModel<MemberResponse>> getMember() {
        MemberResponse response = modelMapper.map(authService.getLoggedInUser(), MemberResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EntityModel<MemberResponse>> updateUser(@RequestBody MemberDto updatedDto) {
        MemberDto savedDto = authService.updateMember(updatedDto);
        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, MemberResponse.class)),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> handleUserUnRegistration() {
        authService.deleteMember();
        return ResponseEntity.ok("User Deleted");
    }

    @PutMapping(path = "/resetPassword")
    public ResponseEntity<?> handleResetPassword(@RequestBody RegistrationRequest request) {
        authService.resetPassword(request.getPassword());
        return ResponseEntity.accepted().build();
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String authenticatedUserId, HttpStatus statusToSend) {
        String token = securityUtils.generateJwtToken(authenticatedUserId);
        return new ResponseEntity<>(Collections.singletonMap("TOKEN", token), statusToSend);
    }
}
