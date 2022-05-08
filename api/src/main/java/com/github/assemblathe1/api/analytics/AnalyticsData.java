package com.github.assemblathe1.api.analytics;

import com.github.assemblathe1.api.analytics.ProductCountDto;
import com.github.assemblathe1.api.core.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsData {
    private List<ProductCountDto> productCountDtoArrayList;
}
