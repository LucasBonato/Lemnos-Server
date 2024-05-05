package com.lemnos.server.exceptions.global;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class TelefoneNotValidException extends BaseException {
    public TelefoneNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.TELEFONE.ordinal(), message));
    }
}
