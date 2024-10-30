package com.praveenukkoji.orderservice.external.product.config;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
