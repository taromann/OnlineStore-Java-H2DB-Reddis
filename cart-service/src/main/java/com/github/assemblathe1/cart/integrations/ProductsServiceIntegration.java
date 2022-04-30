package com.github.assemblathe1.cart.integrations;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ProductServiceAppError;
import com.github.assemblathe1.cart.exceptions.ProductServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto findById(Long id) {
        ProductDto productDto = productServiceWebClient.get() //берем корень микросервиса (вебклаент всегда хранит корень)
                .uri("/api/v1/products/" + id)
                .retrieve() //ожидаем ответ
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(ProductServiceAppError.class).map(
                                body -> {
                                    //проверяем на ошибки, мапим в зависимости от кода ошибки
                                    if (body.getCode().equals(ProductServiceAppError.ProductServiceErrors.PRODUCT_NOT_FOUND.name())) {
                                        return new ProductServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: продукт не найден");
                                    }
                                    return new ProductServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: причина неизвестна");
                                }
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ProductServiceIntegrationException("Сервис продуктов сломался")))
                .bodyToMono(ProductDto.class)
                .block();//ждем выполнения синхронной операции
        return productDto;
    }
}
