package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroCpfAlreadyInUseException extends BaseException {
    public CadastroCpfAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse("O CPF inserido já está sendo utilizado!"));
    }
}
