package ca.group6.apigateway.config;

import ca.group6.apigateway.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProxyConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("batch-service", route -> route
                        .path("/api/v1/batches", "/api/v1/types")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://batch-service")
                )
                .route("sell-service", route -> route
                        .path("/api/v1/sells")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://sell-service")
                )
                .route("discovery-service", route -> route
                        .path("/eureka/web")
                        .filters(f -> f.filter(jwtFilter).setPath("/"))
                        .uri("lb://discovery-service")
                )
                .route("security-service", route -> route
                        .path("/security/**")
                        .uri("lb://security-service")
                )
                .route("discovery-service-static", route -> route
                        .path("/eureka/**")
                        .uri("lb://discovery-service")
                )
                .build();
    }
}
