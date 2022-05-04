package com.github.assemblathe1.api.exceptions.ResourceNotFoundExceptions;

public abstract class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

