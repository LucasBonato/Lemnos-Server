package com.lemnos.server.Exceptions.Global;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class UpdateNotValidException extends BaseException {
    public UpdateNotValidException(String object) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.GLOBAL.ordinal(), object + " não foi atualizado por nada ter sido alterado!"));
    }
}
