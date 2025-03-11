package com.destaxa.payment.api.infraestructure.exception;

import com.destaxa.payment.api.core.exception.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<?> handleAuthorizationException(AuthorizationException authorizationException){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authorizationException.getMessage());
    }

}
