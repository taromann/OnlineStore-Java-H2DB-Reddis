package com.geekbrains.spring.web.api.exceptions;

public class ProductServiceAppError extends AppError {
    public enum ProductServiceErrors {
        PRODUCT_NOT_FOUND, PRODUCT_SERVICE_IS_BROKEN, //создаем варианты ошибок
    }

    public ProductServiceAppError(String code, String message) {
        super(code, message);
    }

    public ProductServiceAppError() {
    }
}
