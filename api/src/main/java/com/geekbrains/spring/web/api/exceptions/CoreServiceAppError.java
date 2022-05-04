package com.geekbrains.spring.web.api.exceptions;

public class CoreServiceAppError extends AppError {
    public enum CoreServiceErrors {
        PRODUCT_NOT_FOUND, PRODUCT_SERVICE_IS_BROKEN, ORDER_NOT_FOUND, ORDER_SERVICE_IS_BROKEN, UNKNOWN_CORE_SERVICE_RESOURCE_NOT_FOUND_EXCEPTION
    }

    public CoreServiceAppError(String code, String message) {
        super(code, message);
    }

    public CoreServiceAppError() {
    }
}
