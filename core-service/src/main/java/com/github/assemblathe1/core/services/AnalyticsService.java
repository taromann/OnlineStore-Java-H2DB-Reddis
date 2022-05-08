package com.github.assemblathe1.core.services;

import com.github.assemblathe1.api.analytics.AnalyticsData;
import com.github.assemblathe1.core.converters.ProductCountConverter;
import com.github.assemblathe1.core.repositories.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;
    private final ProductCountConverter productCountConverter;

    public AnalyticsData getMostPopularProductsPerMonth(Integer productQuantity) {
        return new AnalyticsData(analyticsRepository
                .getMostPopularProductsPerMonth(productQuantity)
                .stream()
                .map(productCountConverter::entityToDto)
                .collect(Collectors.toList()));
    }
}
