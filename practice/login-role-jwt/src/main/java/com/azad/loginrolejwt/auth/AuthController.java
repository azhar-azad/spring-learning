package com.azad.loginrolejwt.auth;

import com.azad.loginrolejwt.entities.User;
import com.azad.loginrolejwt.requests.AuthRequest;
import com.azad.loginrolejwt.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody AuthRequest request) {

        User user = authService.getRegisteredUser(request);

        return generateTokenAndSend(user.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody AuthRequest request) {

        try {
            authService.authenticateUser(request.getEmail(), request.getPassword());

            return generateTokenAndSend(request.getEmail(), HttpStatus.OK);

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus statusToSend) {
        // Generate the JWT
        String token = jwtUtil.generateToken(email);

        // Respond with the JWT
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
