package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroNotValidException extends BaseException {
    public CadastroNotValidException(Integer id, String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(id, message));
    }
}
