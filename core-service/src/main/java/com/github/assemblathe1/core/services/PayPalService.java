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

    //сборка заказа - идея метода в преобразовании нашего заказа в тот что понимает PayPal
    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {
        //по заказу формируем запрос к paypal

        com.github.assemblathe1.core.entities.Order order = orderService
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));
        //формируем запрос на создание заказа на paypal
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        //указываем детали приложения
        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Spring Web Market") //название приложения
                .landingPage("BILLING")//как выглядит страничка для ввода данных
                .shippingPreference("SET_PROVIDED_ADDRESS");//детали доставки (юзер указывает)
        orderRequest.applicationContext(applicationContext);//подшивает инфу о приложении в наш запрос

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();//единицы покупок (что то типа cartItem) - формируем список
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString()) // к какому заказу они относятся
                .description("Spring Web Market Order") //описание продукта
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(String.valueOf(order.getTotalPrice()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(String.valueOf(order.getTotalPrice()))))) // сумма платежа в рублях
                .items(order.getItems().stream()// достаем из заказа orderItem-ы
                        .map(orderItem -> new Item() //преобразуем в Item, которые понимает PayPal
                                .name(orderItem.getProduct().getTitle()) // название продукта
                                .unitAmount(new Money().currencyCode("USD").value(String.valueOf(orderItem.getPrice()))) //цена товара
                                .quantity(String.valueOf(orderItem.getQuantity()))) // количество товара
                        .collect(Collectors.toList()))// пакуем в лист
                //указываем детали доставки
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUsername()))
                        .addressPortable(new AddressPortable()
                                .addressLine1("apt. " + order.getApartment().toString())
                                .addressLine2("house " + order.getHouse().toString())
                                .adminArea2(order.getStreet() + " str")
                                .adminArea1(order.getCity()).countryCode("RU")));
        purchaseUnitRequests.add(purchaseUnitRequest);//добавляем к запросу
        orderRequest.purchaseUnits(purchaseUnitRequests); //формируем Requests
        return orderRequest;
    }
}
