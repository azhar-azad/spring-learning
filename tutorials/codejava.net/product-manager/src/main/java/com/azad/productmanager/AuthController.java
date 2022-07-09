package com.azad.productmanager;

import com.azad.productmanager.security.JWTUtil;
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
@RequestMapping(path = "/api/auth")
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
    public ResponseEntity<Map<String, String>> registerHandler(@RequestBody User appUser) {

//        AppUserDto appUserDto = modelMapper.map(appUser, AppUserDto.class);

        User user = authService.getRegisteredAppUser(appUser);

        return generateTokenAndSend(user.getUsername(), HttpStatus.CREATED);
    }

    // Defining the function to handle the POST route for logging in a user
    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> loginHandler(@RequestBody User loginRequest) {

        try {
            authService.authenticateAppUser(loginRequest.getUsername(), loginRequest.getPassword());

            // If this point is reached it means authentication was successful
            return generateTokenAndSend(loginRequest.getUsername(), HttpStatus.OK);

        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String username, HttpStatus statusToSend) {
        // Generate the JWT
        String token = jwtUtil.generateToken(username);

        // Respond with the JWT
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
