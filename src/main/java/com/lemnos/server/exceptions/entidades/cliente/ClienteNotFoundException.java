package com.lemnos.server.exceptions.entidades.cliente;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class ClienteNotFoundException extends BaseException {
    public ClienteNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL, "Cliente não encontrado!"));
    }
}
