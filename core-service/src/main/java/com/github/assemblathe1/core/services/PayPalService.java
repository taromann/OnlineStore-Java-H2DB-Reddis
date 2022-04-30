package com.github.assemblathe1.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {
    private final OrderService orderService;

    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {
        com.github.assemblathe1.core.entities.Order order = orderService
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Spring Web Market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Spring Web Market Order")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(String.valueOf(order.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(String.valueOf(order.getTotalPrice())))))
                .items(order.getItems().stream()
                        .map(orderItem -> new Item()
                                .name(orderItem.getProduct().getTitle())
                                .unitAmount(new Money().currencyCode("USD").value(String.valueOf(orderItem.getPrice())))
                                .quantity(String.valueOf(orderItem.getQuantity())))
                        .collect(Collectors.toList()))
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUsername()))
                        .addressPortable(new AddressPortable()
                                .addressLine1("apt. " + order.getApartment().toString())
                                .addressLine2("house " + order.getHouse().toString())
                                .adminArea2(order.getStreet() + " str")
                                .adminArea1(order.getCity()).countryCode("RU")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
