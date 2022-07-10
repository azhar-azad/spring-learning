package com.azad.onlineed.security.auth;

import com.azad.onlineed.models.dtos.UserDto;
import com.azad.onlineed.security.jwt.JWTUtil;
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

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody AuthRequest registrationRequest) {

        UserDto userDto = modelMapper.map(registrationRequest, UserDto.class);

        UserDto registeredUserDto = authService.getRegisteredUser(userDto);

        return generateTokenAndSend(registeredUserDto.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody AuthRequest loginRequest) {

        try {
            authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

            return generateTokenAndSend(loginRequest.getEmail(), HttpStatus.OK);

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus statusToSend) {

        String token = jwtUtil.generateToken(email);

        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
