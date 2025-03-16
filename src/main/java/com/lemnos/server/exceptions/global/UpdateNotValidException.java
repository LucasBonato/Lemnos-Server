package com.lemnos.server.exceptions.global;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class UpdateNotValidException extends BaseException {
    public UpdateNotValidException(String object) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.GLOBAL, object + " não foi atualizado por nada ter sido alterado!"));
    }
}
