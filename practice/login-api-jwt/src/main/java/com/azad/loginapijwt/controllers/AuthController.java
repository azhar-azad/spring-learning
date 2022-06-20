package com.azad.loginapijwt.controllers;

import com.azad.loginapijwt.entity.User;
import com.azad.loginapijwt.models.LoginCredentials;
import com.azad.loginapijwt.repository.UserRepo;
import com.azad.loginapijwt.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private UserRepo userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Defining the function to handle the POST route for registering a user
    @PostMapping(path = "/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {

        // Encoding password using bcrypt
        String encodedPass = passwordEncoder.encode(user.getPassword());

        // Setting the encoded password
        user.setPassword(encodedPass);

        // Persisting the User Entity to db
        user = userRepo.save(user);

        // Generating JWT
        String token = jwtUtil.generateToken(user.getEmail());

        // Responding with JWT
        return Collections.singletonMap("token", token);
    }

    // Defining the function to handle the POST route for logging in a user
    @PostMapping(path = "/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials loginCredentials) {

        try {
            // Creating the authentication token which will contain the credentials for authentication.
            // This token is used as input to the authentication process
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword());

            // Authenticating the login credentials
            authManager.authenticate(authInputToken);

            // If this point is reached it means authentication was successful
            // Generate the JWT
            String token = jwtUtil.generateToken(loginCredentials.getEmail());

            // Respond with the JWT
            return Collections.singletonMap("token", token);
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid login credentials");
        }
    }
}
