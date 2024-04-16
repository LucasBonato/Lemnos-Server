package com.koyeb.lemnos.Exceptions.Cadastro;

import com.koyeb.lemnos.Exceptions.BaseException;
import com.koyeb.lemnos.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroCnpjAlreadyInUseException extends BaseException {
    public CadastroCnpjAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse("O CNPJ inserido já está sendo utilizado!"));
    }
}
