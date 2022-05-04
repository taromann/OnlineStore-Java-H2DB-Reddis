package com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions;

public class CartNotFoundException extends ResourceNotFoundException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
