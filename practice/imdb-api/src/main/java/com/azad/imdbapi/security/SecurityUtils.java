package com.azad.imdbapi.security;

import com.azad.imdbapi.entities.ImdbUserEntity;
import com.azad.imdbapi.models.ImdbUser;
import com.azad.imdbapi.security.jwt.JWTUtil;
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
    private JWTUtil jwtUtil;

    public boolean isUsernameBasedAuth() {
        return authBase.equalsIgnoreCase("USERNAME");
    }

    public boolean isEmailBasedAuth() {
        return authBase.equalsIgnoreCase("EMAIL");
    }

    public String getUniqueUserIdentifier(ImdbUserEntity user) {
        if (isUsernameBasedAuth())
            return user.getUsername();
        else if (isEmailBasedAuth())
            return user.getEmail();
        else
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    public <U extends ImdbUser> String getUID(U u) {
        if (isUsernameBasedAuth())
            return u.getUsername();
        else if (isEmailBasedAuth())
            return u.getEmail();
        else
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    public <U extends ImdbUser> ResponseEntity<Map<String, String>> generateTokenAndSend(U u, HttpStatus statusToReturn) {

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
