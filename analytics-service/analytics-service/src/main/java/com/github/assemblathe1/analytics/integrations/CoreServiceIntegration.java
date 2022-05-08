package com.github.assemblathe1.analytics.integrations;

import com.github.assemblathe1.api.analytics.AnalyticsData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;
    public Optional<AnalyticsData> getMostPopularProducts(Integer requestsProperties) {
        AnalyticsData analyticsData = coreServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/analytics")
                        .queryParam("quantity", requestsProperties)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ApplicationContextException("Сервис продуктов сломался")))
                .bodyToMono(AnalyticsData.class)
                .block();
        return Optional.ofNullable(analyticsData);
    }
}
