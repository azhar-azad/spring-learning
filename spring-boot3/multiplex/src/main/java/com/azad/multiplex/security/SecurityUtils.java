package com.azad.multiplex.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.azad.multiplex.model.entity.MemberEntity;
import com.azad.multiplex.repository.MemberRepository;
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

    @Value("${jwt_subject}")
    private String JWT_SUBJECT;

    @Value("${jwt_claim}")
    private String JWT_CLAIM;

    @Value("${jwt_issuer}")
    private String JWT_ISSUER;

    @Autowired
    private MemberRepository repository;

    public MemberEntity getMemberByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
    }

    public String generateJwtToken(String email) {
        return JWT.create()
                .withSubject(JWT_SUBJECT)
                .withClaim(JWT_CLAIM, email)
                .withIssuedAt(new Date())
                .withIssuer(JWT_ISSUER)
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public String validateJwtTokenAndRetrieveClaim(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                .withSubject(JWT_SUBJECT)
                .withIssuer(JWT_ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(JWT_CLAIM).asString();
    }
}
