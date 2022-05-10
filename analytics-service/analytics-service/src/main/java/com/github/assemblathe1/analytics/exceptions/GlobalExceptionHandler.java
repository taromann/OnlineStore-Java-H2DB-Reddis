package com.github.assemblathe1.analytics.exceptions;

import com.github.assemblathe1.api.exceptions.AppError;
import com.github.assemblathe1.api.exceptions.CartServiceAppError;
import com.github.assemblathe1.api.exceptions.ResourceNotFoundExceptions.CartNotFoundException;
import com.github.assemblathe1.api.exceptions.ResourceNotFoundExceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchProductServiceIntegrationException(CoreServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError("CORE_SERVICE_INTEGRATION_ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
