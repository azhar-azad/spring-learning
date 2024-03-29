package com.azad.userauthservice.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String validateJwtTokenAndRetrieveClaim(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("com.azad.user-auth-service")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("UID").asString();
    }

    public String generateJwtToken(String usernameOrEmail) throws IllegalArgumentException, JWTCreationException {

        return JWT.create()
                .withSubject("User Details")
                .withClaim("UID", usernameOrEmail)
                .withIssuedAt(new Date())
                .withIssuer("com.azad.user-auth-service")
                .sign(Algorithm.HMAC256(secret));
    }
}
