package com.lemnos.server.Exceptions.Fornecedor;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class FornecedorNotFoundException extends BaseException {
    public FornecedorNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(0, "Fornecedor não encontrado!"));
    }
}
