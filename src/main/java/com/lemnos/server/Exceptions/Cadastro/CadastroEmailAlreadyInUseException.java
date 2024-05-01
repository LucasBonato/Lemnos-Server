package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroEmailAlreadyInUseException extends BaseException {
    public CadastroEmailAlreadyInUseException(){
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.EMAIL.ordinal(), "O Email inserido já está sendo utilizado!"));
    }
}
