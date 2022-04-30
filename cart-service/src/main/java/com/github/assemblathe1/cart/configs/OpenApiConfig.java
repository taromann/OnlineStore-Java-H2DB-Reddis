package com.github.assemblathe1.cart.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//конфинг сваггера - результат в   /web-market-carts/swagger-ui.html
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Online-store: cart-service")
                                .version("1")
                );
    }
}
