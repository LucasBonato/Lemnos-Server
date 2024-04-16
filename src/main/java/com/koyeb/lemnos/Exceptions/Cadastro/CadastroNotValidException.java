package com.koyeb.lemnos.Exceptions.Cadastro;

import com.koyeb.lemnos.Exceptions.BaseException;
import com.koyeb.lemnos.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroNotValidException extends BaseException {
    public CadastroNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(message));
    }
}
