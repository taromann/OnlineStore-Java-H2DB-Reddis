package com.geekbrains.spring.web.api.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private String city;
    private String street;
    private Integer house;
    private Integer apartment;
    private String phone;
}
