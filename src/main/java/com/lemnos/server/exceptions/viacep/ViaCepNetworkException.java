package com.lemnos.server.exceptions.viacep;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class ViaCepNetworkException extends BaseException {
    public ViaCepNetworkException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new ExceptionResponse(Codigo.GLOBAL, message));
    }
}
