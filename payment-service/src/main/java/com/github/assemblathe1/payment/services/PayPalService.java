package com.github.assemblathe1.payment.services;

import com.github.assemblathe1.api.core.OrderDto;
import com.github.assemblathe1.payment.integrations.CoreServiceIntegration;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {
    private final CoreServiceIntegration coreServiceIntegration;

    public OrderRequest createOrderRequest(Long orderId) {
        OrderDto orderDto = coreServiceIntegration
                .findById(orderId);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Online-store")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                //TODO это описание заказа - изменить
                .description("Online-store Order")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(String.valueOf(orderDto.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(String.valueOf(orderDto.getTotalPrice())))))
                .items(orderDto.getItems().stream()
                        .map(orderItem -> new Item()
                                .name(orderItem.getProductTitle())
                                .unitAmount(new Money().currencyCode("USD").value(String.valueOf(orderItem.getPrice())))
                                .quantity(String.valueOf(orderItem.getQuantity())))
                        .collect(Collectors.toList()))
                .shippingDetail(new ShippingDetail().name(new Name().fullName(orderDto.getUsername()))
                        .addressPortable(new AddressPortable()
                                .addressLine1("apt. " + orderDto.getApartment().toString())
                                .addressLine2("house " + orderDto.getHouse().toString())
                                .adminArea2(orderDto.getStreet() + " str")
                                .adminArea1(orderDto.getCity()).countryCode("RU")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
