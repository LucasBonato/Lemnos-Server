package com.lemnos.server.exceptions.auth;

import com.lemnos.server.exceptions.BaseException;
import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.enums.Codigo;
import org.springframework.http.HttpStatus;

public class UsuarioNotFoundException extends BaseException {
    public UsuarioNotFoundException() {
        super(HttpStatus.NOT_FOUND, new ExceptionResponse(Codigo.GLOBAL, "Usuário não encontrado, verifique suas credenciais"));
    }
}
