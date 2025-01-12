package com.rbnk.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_service", r -> r
                        .path("/auth/**")
                        .uri("http://localhost:8083"))
                .route("auth_service", r -> r
                        .path("/admin/**")
                        .uri("http://localhost:8083"))
                .route("comment_service", r -> r
                        .path("/comments/**")
                        .uri("http://localhost:8082"))
                .route("news_service", r -> r
                        .path("/news/**")
                        .uri("http://localhost:8081"))
                .build();
    }
}
