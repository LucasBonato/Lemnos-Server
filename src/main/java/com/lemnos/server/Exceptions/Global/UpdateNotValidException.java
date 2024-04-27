package com.lemnos.server.Exceptions.Global;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class UpdateNotValidException extends BaseException {
    public UpdateNotValidException(String object) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(0, object + " não foi atualizado por nada ter sido alterado!"));
    }
}
