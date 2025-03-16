package com.lemnos.server.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Situacao {
    ATIVO("ativo"),
    INATIVO("inativo");

    private final String situacao;
}
