package com.azad.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.azad.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value(("${jwt_secret}"))
    private String SECRET;

    public String generateJwtToken(String usernameOrEmail) throws IllegalArgumentException, JWTCreationException {

        return JWT.create()
                .withSubject("User Details")
                .withClaim("UserID", usernameOrEmail)
                .withIssuedAt(new Date())
                .withIssuer("com.azad.todo-mm")
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String validateJwtTokenAndRetrieveClaim(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withSubject("User Details")
                .withIssuer("com.azad.todo-mm")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("UserID").asString();
    }
}
