package com.azad.grocery.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.azad.grocery.models.AppUserEntity;
import com.azad.grocery.repository.AppUserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
public class SecurityUtils {

    @Value("${auth_base}")
    private String AUTH_BASE;

    @Value("${jwt_secret}")
    private String JWT_SECRET;

    @Autowired
    private AppUserRepository repository;

    public boolean isUsernameBasedAuth() {
        return AUTH_BASE.equalsIgnoreCase("USERNAME");
    }

    public boolean isEmailBasedAuth() {
        return AUTH_BASE.equalsIgnoreCase("EMAIL");
    }

    public AppUserEntity getUserByAuthBase(String usernameOrEmail) {

        if (isUsernameBasedAuth())
            return repository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username " + usernameOrEmail));
        else if (isEmailBasedAuth())
            return repository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email " + usernameOrEmail));
        else
            throw new RuntimeException("Unknown Authentication base configured. Valid auth_base values are USERNAME or EMAIL");
    }

    public String generateJwtToken(String usernameOrEmail) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("UserID", usernameOrEmail)
                .withIssuedAt(new Date())
                .withIssuer("com.azad.grocery")
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public String validateJwtTokenAndRetrieveClaim(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                .withSubject("User Details")
                .withIssuer("com.azad.grocery")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("UserID").asString();
    }
}
