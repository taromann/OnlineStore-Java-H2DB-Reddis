package com.github.assemblathe1.api.exceptions;

public class CartServiceAppError extends AppError {
    public enum CartServiceErrors {
        CART_IS_BROKEN, CART_ID_GENERATOR_DISABLED, CART_NOT_FOUND, UNKNOWN_CART_SERVICE_RESOURCE_NOT_FOUND_EXCEPTION //создаем варианты ошибок
    }

    public CartServiceAppError(String code, String message) {
        super(code, message);
    }
}

