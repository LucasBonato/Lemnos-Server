package com.koyeb.lemnos.Exceptions.Cliente;

import com.koyeb.lemnos.Exceptions.BaseException;
import com.koyeb.lemnos.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class ClienteUpdateNotValidException extends BaseException {
    public ClienteUpdateNotValidException() {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse("Cliente não foi atualizado por nada ter sido alterado!"));
    }
}
