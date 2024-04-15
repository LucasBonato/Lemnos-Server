package com.lemnos.server.Exceptions.Cliente;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class ClienteUpdateNotValidException extends BaseException {
    public ClienteUpdateNotValidException() {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse("Cliente não foi atualizado por nada ter sido alterado!"));
    }
}
