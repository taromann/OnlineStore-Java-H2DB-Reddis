package com.github.assemblathe1.analytics.services;

import com.github.assemblathe1.analytics.integrations.CoreServiceIntegration;
import com.github.assemblathe1.analytics.properties.RequestsProperties;
import com.github.assemblathe1.api.analytics.ProductCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Scope("singleton")
@RequiredArgsConstructor
@EnableConfigurationProperties(
        RequestsProperties.class
)
public class AnalyticsService {
    private final CoreServiceIntegration coreServiceIntegration;
    private final RequestsProperties requestsProperties;
    private List<ProductCountDto> productCountDtoArrayList;


    @Scheduled(fixedDelay = 10000)
    @PostConstruct
    public void getAnalytics() {
        System.out.println(coreServiceIntegration.getMostPopularProducts(requestsProperties.getMostPopularOrderedProductQuantity()).get().toString());
        productCountDtoArrayList = coreServiceIntegration
                .getMostPopularProducts(requestsProperties.getMostPopularOrderedProductQuantity())
                .get()
                .getProductCountDtoArrayList();
        System.out.println(productCountDtoArrayList.size());
        for (int i = 0; i < productCountDtoArrayList.size(); i++) {
            System.out.println(productCountDtoArrayList.get(i));
        }
    }

    public Page<ProductCountDto> getMostPopularProducts(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        int start = (int) pageable.getOffset();
        int end = (int) (Math.min((start + pageable.getPageSize()), productCountDtoArrayList.size()));
        return new PageImpl<>(productCountDtoArrayList.subList(start, end), pageable, productCountDtoArrayList.size());
    }
}
