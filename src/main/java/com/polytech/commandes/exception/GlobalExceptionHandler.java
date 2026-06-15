package com.polytech.commandes.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("code", ex.getCode());
        error.put("message", ex.getMessage());

        return ResponseEntity.status(ex.getCode()).body(error);
    }
}
