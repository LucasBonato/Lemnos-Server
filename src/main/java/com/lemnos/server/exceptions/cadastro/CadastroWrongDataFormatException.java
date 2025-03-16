package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class CadastroWrongDataFormatException extends BaseException {
    public CadastroWrongDataFormatException(Codigo codigo) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(codigo, "Data com formatação inválida! (dd/mm/yyyy)"));
    }
}
