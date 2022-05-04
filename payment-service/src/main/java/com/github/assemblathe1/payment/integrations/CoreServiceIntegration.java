package com.github.assemblathe1.payment.integrations;

import com.github.assemblathe1.api.core.OrderDto;
import com.github.assemblathe1.api.exceptions.CoreServiceAppError;
import com.github.assemblathe1.payment.exceptions.OrderServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;

    public String getOrderStatus(Long orderId) {
        String orderStatus = String.valueOf(coreServiceWebClient.get()
                .uri("/api/v1/orders/" + orderId + "/status")
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CoreServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.ORDER_NOT_FOUND.name())) {
                                        return new OrderServiceIntegrationException("Выполнен некорректный запрос к сервису заказов: заказ не найден");
                                    }
                                    return new OrderServiceIntegrationException("Выполнен некорректный запрос к сервису заказов: заказ неизвестна");
                                }
                        )
                )
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new OrderServiceIntegrationException("Сервис заказов сломался")))
                .bodyToMono(String.class)
                .block());
        return orderStatus;
    }

    public String setOrderStatus(long orderId, String status) {
        String orderStatus = String.valueOf(coreServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/orders/" + orderId + "/status")
                                .queryParam("status", status)
                                .build())
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CoreServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.ORDER_NOT_FOUND.name())) {
                                        return new OrderServiceIntegrationException("Выполнен некорректный запрос к сервису заказов: заказ не найден");
                                    }
                                    return new OrderServiceIntegrationException("Выполнен некорректный запрос к сервису заказов: заказ неизвестна");
                                }
                        )
                )
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new OrderServiceIntegrationException("Сервис заказов сломался")))
                .bodyToMono(String.class)
                .block());
        return orderStatus;
    }

    public OrderDto findById(Long orderId) {
        OrderDto orderDto = coreServiceWebClient.get()
                .uri("/api/v1/orders/" + orderId)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CoreServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.ORDER_NOT_FOUND.name())) {
                                        return new OrderServiceIntegrationException("Выполнен некорректный запрос к сервису заказов: заказ не найден");
                                    }
                                    return new OrderServiceIntegrationException("Выполнен некорректный запрос к сервису заказов: заказ неизвестна");
                                }
                        )
                )
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new OrderServiceIntegrationException("Сервис заказов сломался")))
                .bodyToMono(OrderDto.class)
                .block();
        return orderDto;
    }


}
