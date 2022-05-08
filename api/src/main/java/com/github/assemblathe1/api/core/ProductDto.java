package com.github.assemblathe1.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;

    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Коробка конфет")
    private String title;

    @Schema(description = "Цена продукта", required = true, example = "120")
    private BigDecimal price;

    @Schema(description = "Ссылка на картинку", required = true, example = "https://avatars.mds.yandex.net/i?id=d20fd6f86b1af498f3e82713272721b0-4504894-images-thumbs&n=13&exp=1")
    private String pictureLink;
}
