package com.koyeb.lemnos.Exceptions.Cadastro;

import com.koyeb.lemnos.Exceptions.BaseException;
import com.koyeb.lemnos.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroCpfAlreadyInUseException extends BaseException {
    public CadastroCpfAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse("O CPF inserido já está sendo utilizado!"));
    }
}
