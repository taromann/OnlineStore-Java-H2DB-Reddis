package com.github.assemblathe1.core.services;

import com.github.assemblathe1.api.carts.CartDto;
import com.github.assemblathe1.api.core.OrderDetailsDto;
import com.github.assemblathe1.api.exceptions.ResourceNotFoundExceptions.OrderNotFoundException;
import com.github.assemblathe1.api.exceptions.ResourceNotFoundExceptions.ProductNotFoundException;
import com.github.assemblathe1.core.entities.Order;
import com.github.assemblathe1.core.entities.OrderItem;
import com.github.assemblathe1.core.entities.OrderStatus;
import com.github.assemblathe1.core.integrations.CartServiceIntegration;
import com.github.assemblathe1.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
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
        Arrays.stream(orderDetailsDto.getProductIdSet()).forEach(System.out::println);
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setCity(orderDetailsDto.getCity());
        order.setStreet(orderDetailsDto.getStreet());
        order.setHouse(orderDetailsDto.getHouse());
        order.setApartment(orderDetailsDto.getApartment());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setOrderStatus(OrderStatus.CREATED);
        List<OrderItem> items = currentCart.getItems().stream()
                .filter(o -> Arrays.asList(orderDetailsDto.getProductIdSet()).contains(o.getProductId())) //добавляем товары из корзины только те, которые переданы в orderDetailsDto (отмечены галочкой)
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        //считаем сумму заказа исходя из выбранных позиций в карзине при заказе
        order.setTotalPrice(items.stream()
                .map(o -> o.getPricePerProduct().multiply(BigDecimal.valueOf(o.getQuantity())))
                .reduce(BigDecimal::add).get());
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
        Order order = findById(orderId).orElseThrow(() -> new OrderNotFoundException("ORDER 404"));
        order.setOrderStatus(orderStatus);
        return getOrderStatus(orderId);
    }

    public String getOrderStatus(long orderId) {
       return findById(orderId).orElseThrow(() -> new OrderNotFoundException("ORDER 404")).getOrderStatus().name();
    }
}
