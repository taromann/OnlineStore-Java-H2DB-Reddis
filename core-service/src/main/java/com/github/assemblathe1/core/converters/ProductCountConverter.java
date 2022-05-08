package com.github.assemblathe1.core.converters;


import com.github.assemblathe1.api.analytics.ProductCountDto;
import com.github.assemblathe1.core.entities.ProductCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCountConverter {
    private final ProductConverter productConverter;

    public ProductCount dtoToEntity(ProductCountDto productCountDto) {
        return new ProductCount(productConverter.dtoToEntity(productCountDto.getProductDto()), productCountDto.getTotal());
    }

    public ProductCountDto entityToDto(ProductCount productCount) {
        return new ProductCountDto(productConverter.entityToDto(productCount.getProduct()), productCount.getTotal());
    }
}
