package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroEmailAlreadyInUseException extends BaseException {
    public CadastroEmailAlreadyInUseException(){
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.EMAIL, "O Email inserido já está sendo utilizado!"));
    }
}
