package com.koyeb.lemnos.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatus status;
    private ExceptionResponse exceptionResponse;
}
