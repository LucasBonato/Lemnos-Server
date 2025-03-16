package com.lemnos.server.exceptions.auth;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class TokenNotValidOrExpiredException extends BaseException {
    public TokenNotValidOrExpiredException() {
        super(HttpStatus.UNAUTHORIZED, new ExceptionResponse(Codigo.GLOBAL ,"Precisa se estar logado para fazer tal ação"));
    }
}
