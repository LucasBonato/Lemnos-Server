package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class NumeroLogradouroNotValidException extends BaseException {
    public NumeroLogradouroNotValidException() {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.NUMERO_LOGRADOURO, "Número do Logradouro inválido: utilize só números"));
    }
}
