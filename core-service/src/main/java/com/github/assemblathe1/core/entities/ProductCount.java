package com.github.assemblathe1.core.entities;

import com.github.assemblathe1.api.core.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCount {
    private Product product;
    private Long total;
}
