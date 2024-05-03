package com.lemnos.server.Models.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Situacao {
    ATIVO("ativo"),
    INATIVO("inativo");

    private final String situacao;
}
