package com.lemnos.server.exceptions.endereco;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class EstadoNotFoundException extends BaseException {
    public EstadoNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.UF.ordinal(), "Estado não encontrado!"));
    }
}
