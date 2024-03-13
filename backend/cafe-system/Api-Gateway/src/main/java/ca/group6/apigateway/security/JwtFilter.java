package ca.group6.apigateway.security;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtFilter implements GatewayFilter {

    private final JwtCore jwtCore;
    private final RouteValidator routeValidator;

    @Autowired
    public JwtFilter(JwtCore jwtCore, RouteValidator routeValidator) {
        this.jwtCore = jwtCore;
        this.routeValidator = routeValidator;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {
            if (isAuthHeaderMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = getToken(request);

            try {
                jwtCore.validateToken(token);
            } catch (JwtException e) {
                log.warn("Invalid token. {}", e.getMessage());
                return onError(exchange, HttpStatus.FORBIDDEN);
            }

            updateRequest(exchange, token);
        }

        return chain.filter(exchange);
    }

    private boolean isAuthHeaderMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getOrEmpty("Authorization").getFirst();
        return authHeader.substring(7);
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        String username = jwtCore.extractUsername(token);
        String commaSeparatedListOfAuthorities = jwtCore.extractAuthorities(token);

        exchange.getRequest().mutate()
                .header("username", username)
                .header("authorities", commaSeparatedListOfAuthorities)
                .build();
    }
}
