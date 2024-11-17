package com.praveenukkoji.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator RouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/store/api/v1/users/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://USER-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/roles/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://USER-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/addresses/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://USER-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/products/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/categories/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/orders/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ORDER-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/payments/**")
                        .filters(f ->
                                f.rewritePath("/store/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://PAYMENT-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/notifications/**")
                        .filters(f ->
                                f.rewritePath("/store/api/v1/notifications/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://NOTIFICATION-SERVICE")
                )
                .route(p -> p
                        .path("/store/api/v1/config-server/**")
                        .filters(f ->
                                f.rewritePath("/store/api/v1/config-server/(?<segment>.*)", "/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CONFIG-SERVER")
                )
                .build();
    }
}
