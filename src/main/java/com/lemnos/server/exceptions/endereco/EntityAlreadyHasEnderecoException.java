package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class EntityAlreadyHasEnderecoException extends BaseException {
    public EntityAlreadyHasEnderecoException(String entidade) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL.ordinal(), entidade + " já possui este endereço"));
    }
    public EntityAlreadyHasEnderecoException(String entidade, String message) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL.ordinal(), entidade + " " + message));
    }
}