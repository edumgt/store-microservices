package com.praveenukkoji.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges ->
                        // Allow GET requests for specified paths
                        exchanges.pathMatchers(HttpMethod.GET, "/store/api/v1/products/all").permitAll()
                                .pathMatchers(HttpMethod.GET, "/store/api/v1/products").permitAll() // For ?productId=
                                .pathMatchers(HttpMethod.GET, "/store/api/v1/products/get-by-category").permitAll() // For ?categoryName=
                                .pathMatchers(HttpMethod.GET, "/store/api/v1/products/image").permitAll() // For ?imageId=
                                .pathMatchers(HttpMethod.GET, "/store/api/v1/categories/all").permitAll()

                                // Authenticate other requests
                                .pathMatchers("/store/api/v1/users/**").hasRole("USERS")
                                .pathMatchers("/store/api/v1/roles/**").hasRole("ROLES")
                                .pathMatchers("/store/api/v1/addresses/**").hasRole("ADDRESSES")
                                .pathMatchers("/store/api/v1/products/**").hasRole("PRODUCTS")
                                .pathMatchers("/store/api/v1/categories/**").hasRole("CATEGORIES")
                                .pathMatchers("/store/api/v1/orders/**").hasRole("ORDERS")
                                .pathMatchers("/store/api/v1/payments/**").hasRole("PAYMENTS")
                                .pathMatchers("/store/api/v1/notifications/**").hasRole("ADMIN")
                                .pathMatchers("/store/api/v1/config-server/**").hasRole("ADMIN"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return serverHttpSecurity.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
