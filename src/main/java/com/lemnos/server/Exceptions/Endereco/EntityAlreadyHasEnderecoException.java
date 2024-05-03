package com.lemnos.server.Exceptions.Endereco;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class EntityAlreadyHasEnderecoException extends BaseException {
    public EntityAlreadyHasEnderecoException(String entidade) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL.ordinal(), entidade + " já possui este endereço"));
    }
    public EntityAlreadyHasEnderecoException(String entidade, String message) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL.ordinal(), entidade + " " + message));
    }
}