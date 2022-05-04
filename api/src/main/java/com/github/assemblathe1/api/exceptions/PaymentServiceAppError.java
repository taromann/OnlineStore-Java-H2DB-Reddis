package com.github.assemblathe1.api.exceptions;

public class PaymentServiceAppError extends AppError {
    public enum PaymentServiceErrors {
        PAYMENT_IS_BROKEN
    }

    public PaymentServiceAppError(String code, String message) {
        super(code, message);
    }
}

