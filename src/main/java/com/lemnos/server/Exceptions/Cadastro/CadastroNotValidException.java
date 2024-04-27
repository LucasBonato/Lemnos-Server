package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroNotValidException extends BaseException {
    public CadastroNotValidException(Integer id, String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(id, message));
    }
}
