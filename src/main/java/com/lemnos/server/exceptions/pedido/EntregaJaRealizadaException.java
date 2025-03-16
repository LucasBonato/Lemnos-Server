package com.lemnos.server.exceptions.pedido;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class EntregaJaRealizadaException extends BaseException {
    public EntregaJaRealizadaException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL, "Entrega já foi realizada"));
    }
}
