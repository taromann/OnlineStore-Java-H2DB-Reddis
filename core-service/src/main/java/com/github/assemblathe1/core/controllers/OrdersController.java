package com.github.assemblathe1.core.controllers;

import com.geekbrains.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.spring.web.api.core.OrderDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions.OrderNotFoundException;
import com.github.assemblathe1.core.converters.OrderConverter;
import com.github.assemblathe1.core.entities.OrderStatus;
import com.github.assemblathe1.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username)
                .stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/status")
    public String setOrderStatus(
            @PathVariable Long id,
            @RequestParam(name = "status") String orderStatus
            ) {
        return orderService.setOrderStatus(id, OrderStatus.valueOf(orderStatus));
    }

    @GetMapping("/{id}/status")
    public String getOrderStatus(@PathVariable Long id) {
        return orderService.getOrderStatus(id);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new OrderNotFoundException("ORDER 404")));
    }
}
