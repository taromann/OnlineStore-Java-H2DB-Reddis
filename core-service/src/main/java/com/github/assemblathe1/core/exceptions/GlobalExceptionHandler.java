package com.github.assemblathe1.core.exceptions;

import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.api.exceptions.CoreServiceAppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions.OrderNotFoundException;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions.ProductNotFoundException;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundExceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        if (e.getClass().equals(ProductNotFoundException.class))
            return new ResponseEntity<>(new CoreServiceAppError(CoreServiceAppError.CoreServiceErrors.PRODUCT_NOT_FOUND.name(), e.getMessage()), HttpStatus.NOT_FOUND);


        if (e.getClass().equals(OrderNotFoundException.class))
            return new ResponseEntity<>(new CoreServiceAppError(CoreServiceAppError.CoreServiceErrors.ORDER_NOT_FOUND.name(), e.getMessage()), HttpStatus.NOT_FOUND);


        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CoreServiceAppError(CoreServiceAppError.CoreServiceErrors.UNKNOWN_CORE_SERVICE_RESOURCE_NOT_FOUND_EXCEPTION.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchCartServiceIntegrationException(CartServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError("CART_SERVICE_INTEGRATION_ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }
}
