package com.lemnos.server.models.dtos.requests;

public record FuncionarioFiltroRequest(
        String nome,
        Integer page,
        Integer size
) {
    public FuncionarioFiltroRequest (String nome, Integer page, Integer size){
        this.nome = nome;
        this.page = page;
        this.size = 5;
    }
}
