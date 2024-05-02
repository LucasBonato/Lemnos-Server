package com.lemnos.server.Exceptions.Endereco;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class EnderecoNotValidException extends BaseException {
    public EnderecoNotValidException(Integer codigo, String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(codigo, message));
    }
}
