package com.lemnos.server.exceptions.produto;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class AvaliacaoNotValidException extends BaseException {
    public AvaliacaoNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.AVALIACAO, message));
    }
}
