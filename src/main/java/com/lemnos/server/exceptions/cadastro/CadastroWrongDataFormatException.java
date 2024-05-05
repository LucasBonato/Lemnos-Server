package com.lemnos.server.exceptions.cadastro;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroWrongDataFormatException extends BaseException {
    public CadastroWrongDataFormatException(Integer id) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(id, "Data com formatação inválida! (dd/mm/yyyy)"));
    }
}
