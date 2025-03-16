package com.lemnos.server.models.enums;

import lombok.Getter;

@Getter
public enum Entidade {
    CLIENTE("Cliente"),
    FUNCIONARIO("Funcionário"),
    FORNECEDOR("Fornecedor");
    
    private final String entidade;
    
    Entidade(String entidade) {
        this.entidade = entidade;
    }
}
