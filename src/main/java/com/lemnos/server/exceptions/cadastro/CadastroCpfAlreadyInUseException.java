package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroCpfAlreadyInUseException extends BaseException {
    public CadastroCpfAlreadyInUseException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.CPF, "O CPF inserido já está sendo utilizado!"));
    }
}
