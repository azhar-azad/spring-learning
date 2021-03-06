package com.azad.school.auth;

import com.azad.school.models.Role;
import com.azad.school.models.User;
import com.azad.school.models.dtos.StudentDto;
import com.azad.school.models.dtos.UserDto;
import com.azad.school.models.requests.LoginRequest;
import com.azad.school.models.requests.RegistrationRequest;
import com.azad.school.security.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Defining the function to handle the POST route for registering a user
    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> registerHandler(@Valid @RequestBody RegistrationRequest request) {

        String registeredEmail = authService.getRegisteredEmail(request);

        return generateTokenAndSend(registeredEmail, HttpStatus.CREATED);
    }

    // Defining the function to handle the POST route for logging in a user
    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> loginHandler(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

            // If this point is reached means authentication was successful.
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
