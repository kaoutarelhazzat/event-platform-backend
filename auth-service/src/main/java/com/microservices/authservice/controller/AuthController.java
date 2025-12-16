package com.microservices.authservice.controller;

import com.microservices.authservice.dto.JwtResponse;
import com.microservices.authservice.dto.LoginRequest;
import com.microservices.authservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration}") long expiration
    ) {
        this.jwtUtil = new JwtUtil(secret, expiration);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {

        // ⚠️ TEMPORAIRE (sans DB)
        if (!"admin".equals(request.getUsername()) ||
                !"admin".equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                request.getUsername(),
                List.of("ROLE_ADMIN")
        );

        return new JwtResponse(token);
    }
}
