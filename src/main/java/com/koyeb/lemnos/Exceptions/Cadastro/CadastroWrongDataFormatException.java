package com.koyeb.lemnos.Exceptions.Cadastro;

import com.koyeb.lemnos.Exceptions.BaseException;
import com.koyeb.lemnos.Exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;

public class CadastroWrongDataFormatException extends BaseException {
    public CadastroWrongDataFormatException() {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse("Data com formatação inválida! (dd/mm/yyyy)"));
    }
}
