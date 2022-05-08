package com.github.assemblathe1.analytics.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "analytics.requests-parameters")
@Data
public class RequestsProperties {
    private Integer mostPopularOrderedProductQuantity;
}
