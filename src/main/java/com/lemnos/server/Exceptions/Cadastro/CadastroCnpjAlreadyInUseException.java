package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroCnpjAlreadyInUseException extends BaseException {
    public CadastroCnpjAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse(8, "O CNPJ inserido já está sendo utilizado!"));
    }
}
