package com.lemnos.server.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(BaseException exception){
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getExceptionResponse());
    }
}
