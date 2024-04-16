package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroEmailAlreadyInUseException extends BaseException {
    public CadastroEmailAlreadyInUseException(){
        super(HttpStatus.CONFLICT, new ExceptionResponse("O Email inserido já está sendo utilizado!"));
    }
}
