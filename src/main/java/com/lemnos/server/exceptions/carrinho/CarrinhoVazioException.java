package com.lemnos.server.exceptions.carrinho;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CarrinhoVazioException extends BaseException {
    public CarrinhoVazioException() {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(Codigo.CARRINHO, "Carrinho está vazio"));
    }
}
