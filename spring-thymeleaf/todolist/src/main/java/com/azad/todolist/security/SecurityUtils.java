package com.azad.todolist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.azad.todolist.models.auth.MemberEntity;
import com.azad.todolist.repositorites.MemberRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
public class SecurityUtils {

    @Value("${jwt_secret}")
    private String JWT_SECRET;

    @Autowired
    private MemberRepository repository;

    public MemberEntity getUserByEmail(String email) {

        return repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email " + email));
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
