package com.azad.onlinecourse.api.resource;

import com.azad.onlinecourse.api.assembler.AppUserResponseAssembler;
import com.azad.onlinecourse.models.auth.AppUserDto;
import com.azad.onlinecourse.models.auth.AppUserResponse;
import com.azad.onlinecourse.models.auth.LoginRequest;
import com.azad.onlinecourse.models.auth.RegistrationRequest;
import com.azad.onlinecourse.security.SecurityUtils;
import com.azad.onlinecourse.service.AuthService;
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
    private final AppUserResponseAssembler assembler;

    @Autowired
    public AuthRestResource(AuthService authService, AppUserResponseAssembler assembler) {
        this.authService = authService;
        this.assembler = assembler;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> handleUserRegistration(@Valid @RequestBody RegistrationRequest request) {

        AppUserDto dto = modelMapper.map(request, AppUserDto.class);
        AppUserDto registeredUser = authService.registerUser(dto);

        return generateTokenAndSend(registeredUser.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> handleUserLogin(@Valid @RequestBody LoginRequest request) {

        try {
            String authenticatedUsernameOrEmail = authService.authenticateUserAndGetEmail(request);
            return generateTokenAndSend(authenticatedUsernameOrEmail, HttpStatus.ACCEPTED);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<EntityModel<AppUserResponse>> getUser() {
        AppUserResponse response = modelMapper.map(authService.getLoggedInUser(), AppUserResponse.class);
        return new ResponseEntity<>(assembler.toModel(response), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EntityModel<AppUserResponse>> updateUser(@RequestBody AppUserDto updatedDto) {
        AppUserDto savedDto = authService.updateUser(updatedDto);
        return new ResponseEntity<>(assembler.toModel(modelMapper.map(savedDto, AppUserResponse.class)),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> handleUserUnRegistration() {
        authService.deleteUser();
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
