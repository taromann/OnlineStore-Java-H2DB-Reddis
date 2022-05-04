package com.github.assemblathe1.core.integrations;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.exceptions.CartServiceAppError;
import com.github.assemblathe1.core.exceptions.CartServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public void clearUserCart(String username) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getUserCart(String username) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                // .bodyValue(body) // for POST
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                this::getCartServiceIntegrationException
                        )
                )
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин")))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис корзин сломался")))
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }

    private CartServiceIntegrationException getCartServiceIntegrationException(CartServiceAppError body) {
        if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name())) {
            return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина не найдена");
        }

        if (body.getCode().equals(CartServiceAppError.CartServiceErrors.UNKNOWN_CART_SERVICE_RESOURCE_NOT_FOUND_EXCEPTION.name())) {
            return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина не найдена - причина неизвестна");
        }

        if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name())) {
            return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина сломана");
        }

        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: причина неизвестна");
    }
}
