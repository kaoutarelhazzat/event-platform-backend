package com.microservices.gatewayservice.security;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final JwtProperties props;
    private final AntPathMatcher matcher = new AntPathMatcher();

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/**",
            "/actuator/**",
            "/eureka/**"
    );

    public AuthFilter(JwtUtil jwtUtil, JwtProperties props) {
        this.jwtUtil = jwtUtil;
        this.props = props;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        if (isPublic(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(props.getHeader());

        if (authHeader == null || !authHeader.startsWith(props.getPrefix())) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(props.getPrefix().length());

        try {
            Claims claims = jwtUtil.parseClaims(token);

            ServerWebExchange mutated = exchange.mutate()
                    .request(r -> r.headers(headers -> {
                        headers.set("X-User-Id", jwtUtil.getUserId(claims));
                        headers.set("X-Roles", String.join(",", jwtUtil.getRoles(claims)));
                    }))
                    .build();

            return chain.filter(mutated);

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublic(String path) {
        return PUBLIC_PATHS.stream().anyMatch(p -> matcher.match(p, path));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
