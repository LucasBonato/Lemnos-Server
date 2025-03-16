package com.lemnos.server.exceptions.pedido;


import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class PedidoNotFoundException extends BaseException {
    public PedidoNotFoundException(){
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL, "Pedido não encontrado!"));
    }
}
