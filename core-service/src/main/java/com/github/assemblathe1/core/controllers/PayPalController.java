package com.github.assemblathe1.core.controllers;


import com.github.assemblathe1.core.entities.OrderStatus;
import com.github.assemblathe1.core.services.OrderService;
import com.github.assemblathe1.core.services.PayPalService;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
public class PayPalController {
    private final PayPalHttpClient payPalClient;
    private final OrderService orderService;
    private final PayPalService payPalService;

    //черновик заказа
    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
        if (orderService.orderStatusEqualsTo(orderId, OrderStatus.PAID)) return new ResponseEntity<>("Order has already been payed ", HttpStatus.BAD_REQUEST);
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(payPalService.createOrderRequest(orderId));
        HttpResponse<Order> response = payPalClient.execute(request);
//        System.out.println(response.result().id());
        return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
    }

    @PostMapping("/capture/{payPalId}")
    public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(payPalId);
        request.requestBody(new OrderRequest());
        HttpResponse<Order> response = payPalClient.execute(request);
        Order payPalOrder = response.result();
        long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceId());
        if ("COMPLETED".equals(payPalOrder.status())) {
            orderService.changeOrderStatus(orderId, OrderStatus.PAID);
            return new ResponseEntity<>("Order completed! " + " id "  + orderId, HttpStatus.valueOf(response.statusCode()));
        }

        orderService.changeOrderStatus(orderId, OrderStatus.CANCELLED);
        return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
    }
}
