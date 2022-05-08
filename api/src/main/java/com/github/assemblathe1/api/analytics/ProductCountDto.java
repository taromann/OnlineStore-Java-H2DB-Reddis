package com.github.assemblathe1.api.analytics;

import com.github.assemblathe1.api.core.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCountDto {
    private ProductDto productDto;
    private Long total;


}


