package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroNotValidException extends BaseException {
    public CadastroNotValidException(Codigo codigo, String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(codigo, message));
    }
}
