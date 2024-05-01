package com.lemnos.server.Exceptions.Global;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class TelefoneNotValidException extends BaseException {
    public TelefoneNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.TELEFONE.ordinal(), message));
    }
}
