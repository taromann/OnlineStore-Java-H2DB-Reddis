package com.github.assemblathe1.analytics.converters;

import com.github.assemblathe1.analytics.entities.ProductCount;
import com.github.assemblathe1.api.analytics.ProductCountDto;
import com.github.assemblathe1.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCountConverter {

    public ProductCount dtoToEntity(ProductCountDto productCountDto) {
        return new ProductCount(
                new ProductDto(
                        productCountDto.getProductDto().getId(),
                        productCountDto.getProductDto().getTitle(),
                        productCountDto.getProductDto().getPrice()),
                productCountDto.getTotal()
        );
    }

    public ProductCountDto entityToDto(ProductCount productCount) {
        return new ProductCountDto(
                new ProductDto(
                        productCount.getProductDto().getId(),
                        productCount.getProductDto().getTitle(),
                        productCount.getProductDto().getPrice()),
                productCount.getTotal()
        );
    }
}
