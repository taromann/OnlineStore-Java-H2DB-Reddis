package com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
