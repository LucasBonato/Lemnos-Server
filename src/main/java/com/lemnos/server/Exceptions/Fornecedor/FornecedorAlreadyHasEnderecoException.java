package com.lemnos.server.Exceptions.Fornecedor;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class FornecedorAlreadyHasEnderecoException extends BaseException {
    public FornecedorAlreadyHasEnderecoException() {
        super(HttpStatus.CONFLICT, new ExceptionResponse(Codigo.GLOBAL.ordinal(), "O fornecedor já possui um endereço!"));
    }
}
