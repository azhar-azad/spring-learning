package com.azad.onlineed.security.auth;

import com.azad.onlineed.models.dtos.UserDto;
import com.azad.onlineed.models.responses.ApiResponse;
import com.azad.onlineed.security.entities.UserEntity;
import com.azad.onlineed.security.jwt.JWTUtil;
import com.azad.onlineed.security.requests.LoginRequest;
import com.azad.onlineed.security.requests.RegistrationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {

        UserDto userDto = modelMapper.map(registrationRequest, UserDto.class);

        UserDto registeredUserDto = authService.getRegisteredUser(userDto);

        return generateTokenAndSend(registeredUserDto.getEmail(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

            return generateTokenAndSend(loginRequest.getEmail(), HttpStatus.OK);

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(Collections.singletonMap("Authentication Error", ex.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<ApiResponse> getUserInfo() {

        UserDto userDto = authService.getLoggedInUserInfo();

        return null;
    }

    private ResponseEntity<Map<String, String>> generateTokenAndSend(String email, HttpStatus statusToSend) {

        String token = jwtUtil.generateToken(email);

        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToSend);
    }
}
