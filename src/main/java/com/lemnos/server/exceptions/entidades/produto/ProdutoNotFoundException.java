package com.lemnos.server.exceptions.entidades.produto;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class ProdutoNotFoundException extends BaseException {
    public ProdutoNotFoundException(){
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL, "Produto não encontrado!"));
    }
}
