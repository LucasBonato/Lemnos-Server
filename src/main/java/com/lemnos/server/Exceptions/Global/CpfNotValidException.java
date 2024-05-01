package com.lemnos.server.Exceptions.Global;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class CpfNotValidException extends BaseException {
    public CpfNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.CPF.ordinal(), message));
    }
}
