package com.lemnos.server.Exceptions.Endereco;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class NumeroLogradouroNotValidException extends BaseException {
    public NumeroLogradouroNotValidException() {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.NUMERO_LOGRADOURO.ordinal(), "Número do Logradouro inválido: utilize só números"));
    }
}
