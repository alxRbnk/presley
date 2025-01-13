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
                .route("auth", r -> r
                        .path("/auth/**")
                        .uri("http://auth-service:8083"))
                .route("admin", r -> r
                        .path("/admin/**")
                        .uri("http://auth-service:8083"))
                .route("comment", r -> r
                        .path("/comments/**")
                        .uri("http://comment-service:8082"))
                .route("news", r -> r
                        .path("/news/**")
                        .uri("http://news-service:8081"))
                .build();
    }
}
