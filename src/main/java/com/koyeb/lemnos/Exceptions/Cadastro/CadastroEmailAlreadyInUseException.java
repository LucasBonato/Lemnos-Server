package com.koyeb.lemnos.Exceptions.Cadastro;

import com.koyeb.lemnos.Exceptions.BaseException;
import com.koyeb.lemnos.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroEmailAlreadyInUseException extends BaseException {
    public CadastroEmailAlreadyInUseException(){
        super(HttpStatus.CONFLICT, new ExceptionResponse("O Email inserido já está sendo utilizado!"));
    }
}
