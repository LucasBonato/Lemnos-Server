package com.lemnos.server.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatus status;
    private ExceptionResponse exceptionResponse;
}
