package ca.group6.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("batch-service", route -> route
                        .path("/api/v1/batches", "/api/v1/types")
                        .uri("lb://batch-service")
                )
                .route("sell-service", route -> route
                        .path("/api/v1/sells")
                        .uri("lb://sell-service")
                )
                .route("discovery-service", route -> route
                        .path("/eureka/web")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/"))
                        .uri("lb://discovery-service")
                )
                .route("discovery-service-static", route -> route
                        .path("/eureka/**")
                        .uri("lb://discovery-service")
                )
                .build();
    }
}
