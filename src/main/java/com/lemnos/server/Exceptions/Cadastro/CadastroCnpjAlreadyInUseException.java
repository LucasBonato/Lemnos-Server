package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroCnpjAlreadyInUseException extends BaseException {
    public CadastroCnpjAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.CNPJ.ordinal(), "O CNPJ inserido já está sendo utilizado!"));
    }
}
