package com.lemnos.server.Exceptions.Fornecedor;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class FornecedorNotFoundException extends BaseException {
    public FornecedorNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL.ordinal(), "Fornecedor não encontrado!"));
    }
}
