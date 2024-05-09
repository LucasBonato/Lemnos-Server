package com.lemnos.server.exceptions.entidades.funcionario;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class FuncionarioNotFoundException extends BaseException {
    public FuncionarioNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL.ordinal(), "Funcionário não encontrado!"));
    }
}
