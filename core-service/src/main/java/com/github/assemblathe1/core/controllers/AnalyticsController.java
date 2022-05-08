package com.github.assemblathe1.core.controllers;

import com.github.assemblathe1.api.analytics.AnalyticsData;
import com.github.assemblathe1.core.converters.ProductCountConverter;
import com.github.assemblathe1.core.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final ProductCountConverter productCountConverter;
    private final AnalyticsService analyticsService;

    @GetMapping()
    public AnalyticsData getMostPopularProductsPerMonth(@RequestParam(name = "quantity", defaultValue = "10") Integer productQuantity) {
        return analyticsService.getMostPopularProductsPerMonth(productQuantity);
    }
}
