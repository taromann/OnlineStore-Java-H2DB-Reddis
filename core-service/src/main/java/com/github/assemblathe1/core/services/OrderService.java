package com.github.assemblathe1.core.services;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.github.assemblathe1.core.entities.Order;
import com.github.assemblathe1.core.entities.OrderItem;
import com.github.assemblathe1.core.entities.OrderStatus;
import com.github.assemblathe1.core.integrations.CartServiceIntegration;
import com.github.assemblathe1.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setCity(orderDetailsDto.getCity());
        order.setStreet(orderDetailsDto.getStreet());
        order.setHouse(orderDetailsDto.getHouse());
        order.setApartment(orderDetailsDto.getApartment());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        order.setOrderStatus(OrderStatus.CREATED);
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

    @Transactional
    public String setOrderStatus(long orderId, OrderStatus orderStatus) {
        Order order = findById(orderId).orElseThrow(() -> new ResourceNotFoundException("ORDER 404"));
        order.setOrderStatus(orderStatus);
        return getOrderStatus(orderId);
    }

    @Transactional
    public String getOrderStatus(long orderId) {
       Order order = findById(orderId).orElseThrow(() -> new ResourceNotFoundException("ORDER 404"));
       return order.getOrderStatus().name();
    }
}
