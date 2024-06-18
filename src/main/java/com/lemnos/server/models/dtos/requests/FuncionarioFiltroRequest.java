package com.lemnos.server.models.dtos.requests;

public record FuncionarioFiltroRequest(
        String nome,
        Integer page,
        Integer size
) {
}
