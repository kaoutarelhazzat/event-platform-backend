package com.microservices.gatewayservice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * Secret must be at least 32 bytes for HS256.
     */
    private String secret;

    /**
     * Example: "Bearer "
     */
    private String prefix = "Bearer ";

    /**
     * Example: "Authorization"
     */
    private String header = "Authorization";

    // getters & setters
    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getHeader() { return header; }
    public void setHeader(String header) { this.header = header; }
}
