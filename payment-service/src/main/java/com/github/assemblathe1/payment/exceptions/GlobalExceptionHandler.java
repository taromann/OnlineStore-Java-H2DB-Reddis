package com.github.assemblathe1.payment.exceptions;

import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.api.exceptions.CoreServiceAppError;
import com.geekbrains.spring.web.api.exceptions.PaymentServiceAppError;
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
    public ResponseEntity<AppError> catchOrderServiceIntegrationException(OrderServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError("ORDER_SERVICE_INTEGRATION_ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }
}
