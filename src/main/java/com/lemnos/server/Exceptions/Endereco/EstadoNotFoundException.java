package com.lemnos.server.Exceptions.Endereco;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import com.lemnos.server.Models.Enums.Codigo;
import org.springframework.http.HttpStatus;

public class EstadoNotFoundException extends BaseException {
    public EstadoNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.UF.ordinal(), "Estado não encontrado!"));
    }
}
