package com.lemnos.server.Exceptions.Cliente;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class ClienteNotFoundException extends BaseException {
    public ClienteNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL.ordinal(), "Cliente não encontrado!"));
    }
}
