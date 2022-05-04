package com.github.assemblathe1.api.exceptions.ResourceNotFoundExceptions;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
