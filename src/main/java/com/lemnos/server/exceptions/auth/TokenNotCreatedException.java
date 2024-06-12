package com.lemnos.server.exceptions.auth;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class TokenNotCreatedException extends BaseException {
    public TokenNotCreatedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new ExceptionResponse(Codigo.GLOBAL, "Erro ao criar token!"));
    }
}
