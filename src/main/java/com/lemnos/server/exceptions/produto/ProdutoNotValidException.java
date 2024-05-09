package com.lemnos.server.exceptions.produto;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class ProdutoNotValidException extends BaseException {
    public ProdutoNotValidException(int codigo, String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(codigo, message));
    }
}
