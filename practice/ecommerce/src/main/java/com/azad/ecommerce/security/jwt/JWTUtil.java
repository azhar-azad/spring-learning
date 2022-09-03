package com.azad.ecommerce.security.jwt;

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
public class JWTUtil {

    @Value("${authentication_base}")
    private String authenticationBase;

    @Value("${jwt_secret}")
    private String secret;

    public String validateJwtTokenAndRetrieveClaim(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("com.azad.ecommerce")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public String generateJwtToken(String usernameOrEmail) throws IllegalArgumentException, JWTCreationException {
        if (authenticationBase.equalsIgnoreCase("USERNAME")) {
            return generateJwtTokenForUsername(usernameOrEmail);
        }
        else if (authenticationBase.equalsIgnoreCase("EMAIL")) {
            return generateJwtTokenForEmail(usernameOrEmail);
        }
        else
            throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    private String generateJwtTokenForUsername(String username) throws IllegalArgumentException, JWTCreationException {

        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("com.azad.ecommerce")
                .sign(Algorithm.HMAC256(secret));
    }

    private String generateJwtTokenForEmail(String email) throws IllegalArgumentException, JWTCreationException {

        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("com.azad.ecommerce")
                .sign(Algorithm.HMAC256(secret));
    }
}
