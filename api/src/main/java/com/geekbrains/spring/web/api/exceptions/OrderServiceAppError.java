package com.geekbrains.spring.web.api.exceptions;

public class OrderServiceAppError extends AppError {
    public enum OrderServiceErrors {
       ORDER_NOT_FOUND, ORDER_SERVICE_IS_BROKEN, //создаем варианты ошибок
    }

    public OrderServiceAppError(String code, String message) {
        super(code, message);
    }

    public OrderServiceAppError() {
    }
}
