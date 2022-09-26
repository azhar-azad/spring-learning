package com.azad.userauthservice.security;

import com.azad.userauthservice.models.AppUser;
import com.azad.userauthservice.models.entities.AppUserEntity;
import com.azad.userauthservice.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class SecurityUtils {

    @Value("${authentication_base}")
    private String authBase;

    @Autowired
    private JwtUtil jwtUtil;

    public boolean isUsernameBasedAuth() {
        return authBase.equalsIgnoreCase("USERNAME");
    }

    public boolean isEmailBasedAuth() {
        return authBase.equalsIgnoreCase("EMAIL");
    }

    public String getUniqueIdentifier(AppUserEntity user) {
        if (isUsernameBasedAuth())
            return user.getUsername();
        else if (isEmailBasedAuth())
            return user.getEmail();
        else
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    public <U extends AppUser>ResponseEntity<Map<String, String>> generateTokenAndSend(U u, HttpStatus statusToReturn) {

        if (isUsernameBasedAuth())
            return generateTokenAndSend(u.getUsername(), statusToReturn);
        else if (isEmailBasedAuth())
            return generateTokenAndSend(u.getEmail(), statusToReturn);
        else
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    public ResponseEntity<Map<String, String>> generateTokenAndSend(String usernameOrEmail, HttpStatus statusToReturn) {
        String token = jwtUtil.generateJwtToken(usernameOrEmail);
        return new ResponseEntity<>(Collections.singletonMap("token", token), statusToReturn);
    }
}
