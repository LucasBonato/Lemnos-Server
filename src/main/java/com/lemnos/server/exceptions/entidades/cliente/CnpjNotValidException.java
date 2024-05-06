package com.lemnos.server.exceptions.cliente;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CnpjNotValidException extends BaseException {
    public CnpjNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.CNPJ.ordinal(), message));
    }
}
