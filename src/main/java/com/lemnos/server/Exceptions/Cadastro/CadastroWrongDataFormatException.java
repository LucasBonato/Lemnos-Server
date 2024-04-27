package com.lemnos.server.Exceptions.Cadastro;

import com.lemnos.server.Exceptions.BaseException;
import com.lemnos.server.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroWrongDataFormatException extends BaseException {
    public CadastroWrongDataFormatException(Integer id) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(id, "Data com formatação inválida! (dd/mm/yyyy)"));
    }
}
