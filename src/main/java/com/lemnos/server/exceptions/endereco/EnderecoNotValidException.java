package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class EnderecoNotValidException extends BaseException {
    public EnderecoNotValidException(Integer codigo, String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(codigo, message));
    }
}
