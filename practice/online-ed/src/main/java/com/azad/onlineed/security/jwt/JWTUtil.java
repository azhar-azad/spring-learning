package com.azad.onlineed.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.azad.onlineed.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private final String secret = Constants.JWT_SECRET;

    /**
     * Sign and create a JWT using the injected secret with
     * subject, issuer, time of issue and custom claim "email".
     * */
    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {

        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("com.azad.online-ed")
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Verify the JWT and then decode and extract the user email
     * stored in the payload of the token.
     * */
    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("com.azad.online-ed")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
