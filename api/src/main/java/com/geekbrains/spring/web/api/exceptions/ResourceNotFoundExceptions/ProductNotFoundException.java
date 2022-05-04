package com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
