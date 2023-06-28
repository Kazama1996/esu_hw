package com.esun.hw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException e) {
        String errorMessate = e.getErrorMessage();
        HttpStatus status = e.getStatus();
        return ResponseEntity.status(status).body(errorMessate);
    }
}
