package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Entidade;
import org.springframework.http.HttpStatus;

public class EntityAlreadyHasEnderecoException extends BaseException {
    public EntityAlreadyHasEnderecoException(Entidade entidade) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL, entidade.getEntidade() + " já possui este endereço"));
    }
    public EntityAlreadyHasEnderecoException(Entidade entidade, String message) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL, entidade.getEntidade() + " " + message));
    }
}