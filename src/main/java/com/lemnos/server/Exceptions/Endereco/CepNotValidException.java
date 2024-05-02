package com.lemnos.server.Exceptions.Endereco;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class CepNotValidException extends BaseException {
    public CepNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.CEP.ordinal(), message));
    }
}
