package com.lemnos.server.Exceptions.Global;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class CnpjNotValidException extends BaseException {
    public CnpjNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.CNPJ.ordinal(), message));
    }
}
