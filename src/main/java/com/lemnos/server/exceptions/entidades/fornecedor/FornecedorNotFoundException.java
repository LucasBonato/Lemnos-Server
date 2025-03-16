package com.lemnos.server.exceptions.entidades.fornecedor;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class FornecedorNotFoundException extends BaseException {
    public FornecedorNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL, "Fornecedor não encontrado!"));
    }
}
