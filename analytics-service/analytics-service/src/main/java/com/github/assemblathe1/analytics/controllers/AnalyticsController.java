package com.github.assemblathe1.analytics.controllers;

import com.github.assemblathe1.analytics.services.AnalyticsService;
import com.github.assemblathe1.api.analytics.ProductCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping
    public Page<ProductCountDto> getMostPopularProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        return analyticsService.getMostPopularProducts(page - 1);
    }
}
