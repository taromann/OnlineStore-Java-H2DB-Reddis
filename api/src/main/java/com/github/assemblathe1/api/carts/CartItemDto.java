package com.github.assemblathe1.api.carts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private String pictureLink;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
}
