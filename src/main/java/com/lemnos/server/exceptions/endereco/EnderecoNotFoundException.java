package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class EnderecoNotFoundException extends BaseException {
    public EnderecoNotFoundException(String entity) {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL, entity + " não possui esse endereço!"));
    }
}
