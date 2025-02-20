package com.smart_bike_rent.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.smart_bike_rent.security.dto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    private final UserServiceNew userServiceNew;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 18_000_000);

        UserDto user = userServiceNew.findByLogin(login);

        return JWT.create()
                .withIssuer(login)
                .withClaim("userId", String.valueOf(user.getId()))
                // .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("calendar", user.isCalendar())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("role", user.getRole().name())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                    .build();
            DecodedJWT decoded = verifier.verify(token);

            UserDto user = userServiceNew.findByLogin(decoded.getIssuer());

            return new
                    UsernamePasswordAuthenticationToken(user, null, Arrays.asList(user.getRole()));
        } catch (
                JWTVerificationException exception) {
            // Obsługa wyjątku, gdy token jest nieprawidłowy
            throw new RuntimeException("Invalid JWT token", exception);
        }
    }
    public Long extractUserId(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                    .build();
            DecodedJWT decoded = verifier.verify(token);

            // Pobierz `userId` z payloadu tokenu (załóżmy, że token zawiera `userId` jako klucz w payload)
            String userIdString = decoded.getClaim("userId").asString();

            if (userIdString != null) {
                return Long.valueOf(userIdString);
            } else {
                throw new RuntimeException("Token does not contain userId");
            }
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid JWT token", exception);
        }
    }

}