package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroCnpjAlreadyInUseException extends BaseException {
    public CadastroCnpjAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.CNPJ, "O CNPJ inserido já está sendo utilizado!"));
    }
}
