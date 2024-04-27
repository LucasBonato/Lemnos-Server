package com.lemnos.server.Exceptions.Funcionario;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class FuncionarioNotFoundException extends BaseException {
    public FuncionarioNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(0, "Funcionário não encontrado!"));
    }
}
