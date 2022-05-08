package com.github.assemblathe1.cart.integrations;

import com.github.assemblathe1.api.core.ProductDto;
import com.github.assemblathe1.api.exceptions.CoreServiceAppError;
import com.github.assemblathe1.cart.exceptions.CoreServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CoreServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto findById(Long id) {
        ProductDto productDto = productServiceWebClient.get() //берем корень микросервиса (вебклаент всегда хранит корень)
                .uri("/api/v1/products/" + id)
                .retrieve() //ожидаем ответ
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CoreServiceAppError.class).map(
                                body -> {
                                    //проверяем на ошибки, мапим в зависимости от кода ошибки
                                    return getProductServiceIntegrationException(body);
                                }
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CoreServiceIntegrationException("Сервис продуктов сломался")))
                .bodyToMono(ProductDto.class)
                .block();//ждем выполнения синхронной операции
        return productDto;
    }

    private CoreServiceIntegrationException getProductServiceIntegrationException(CoreServiceAppError body) {
        if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.PRODUCT_NOT_FOUND.name())) {
            return new CoreServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: продукт не найден");
        }

        if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.UNKNOWN_CORE_SERVICE_RESOURCE_NOT_FOUND_EXCEPTION.name())) {
            return new CoreServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: продукт не найден - причина неизвестна");
        }

        return new CoreServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: причина неизвестна");
    }
}
