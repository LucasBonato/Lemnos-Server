package com.lemnos.server.exceptions.viacep;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class ViaCepServerDownException extends BaseException {
    public ViaCepServerDownException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, new ExceptionResponse(Codigo.GLOBAL.ordinal(), "Servidor da ViaCep caiu."));
    }
}
