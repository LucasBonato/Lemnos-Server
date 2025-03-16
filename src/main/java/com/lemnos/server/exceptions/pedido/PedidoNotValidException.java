package com.lemnos.server.exceptions.pedido;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class PedidoNotValidException extends BaseException {
    public PedidoNotValidException(String message){
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.PEDIDO, message));
    }
}
