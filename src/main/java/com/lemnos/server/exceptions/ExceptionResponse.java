package com.lemnos.server.exceptions;

import com.lemnos.server.models.enums.Codigo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private Integer id;
    private String error;

    public ExceptionResponse(Codigo codigo, String error) {
        this.id = codigo.ordinal();
        this.error = error;
    }
}
