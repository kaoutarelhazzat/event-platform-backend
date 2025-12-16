package com.microservices.gatewayservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
public class JwtUtil {

    private final Key signingKey;

    /**
     * Constructeur Spring
     * Le secret est injecté via JwtProperties
     */
    public JwtUtil(JwtProperties props) {
        String secret = props.getSecret();

        if (secret == null || secret.trim().length() < 32) {
            throw new IllegalStateException(
                    "JWT secret is missing or too short (minimum 32 characters required)"
            );
        }

        this.signingKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Parse et valide le token JWT
     */
    public Claims parseClaims(String token) throws JwtException {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);

        return jws.getBody();
    }

    /**
     * Vérifie si le token est valide
     */
    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Récupère l'identifiant utilisateur depuis le token
     */
    public String getUserId(Claims claims) {
        Object userId = claims.get("userId");
        return userId == null ? null : String.valueOf(userId);
    }

    /**
     * Récupère les rôles utilisateur depuis le token
     */
    @SuppressWarnings("unchecked")
    public List<String> getRoles(Claims claims) {
        Object roles = claims.get("roles");

        if (roles instanceof List<?>) {
            return ((List<?>) roles)
                    .stream()
                    .map(String::valueOf)
                    .toList();
        }

        return List.of();
    }
}
