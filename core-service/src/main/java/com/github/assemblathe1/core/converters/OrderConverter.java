package com.github.assemblathe1.core.converters;

import com.geekbrains.spring.web.api.core.OrderDto;
import com.github.assemblathe1.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setCity(order.getCity());
        out.setStreet(order.getStreet());
        out.setHouse(order.getHouse());
        out.setApartment(order.getApartment());
        out.setPhone(order.getPhone());
        out.setTotalPrice(order.getTotalPrice());
        out.setUsername(order.getUsername());
        out.setItems(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()));
        out.setOrderStatus(order.getOrderStatus().name());
        return out;
    }
}
