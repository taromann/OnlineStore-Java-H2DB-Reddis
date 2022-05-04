package com.github.assemblathe1.api.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private Long[] productIdSet;
    private String city;
    private String street;
    private Integer house;
    private Integer apartment;
    private String phone;

    @Override
    public String toString() {
        return "OrderDetailsDto{" +
                "productIdSet=" + productIdSet +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + house +
                ", apartment=" + apartment +
                ", phone='" + phone + '\'' +
                '}';
    }
}
