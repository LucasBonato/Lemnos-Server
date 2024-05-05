package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CepNotValidException extends BaseException {
    public CepNotValidException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.CEP.ordinal(), message));
    }
}
