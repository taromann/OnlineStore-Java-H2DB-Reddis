package com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions;

public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

