package com.lemnos.server.Exceptions.Cliente;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class ClienteNotFoundException extends BaseException {
    public ClienteNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(0, "Cliente não encontrado!"));
    }
}
