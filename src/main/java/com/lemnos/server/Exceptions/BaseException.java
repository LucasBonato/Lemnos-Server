package com.lemnos.server.Exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatus status;
    private ExceptionResponse exceptionResponse;
}
