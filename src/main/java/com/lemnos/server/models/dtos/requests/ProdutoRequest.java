package com.lemnos.server.models.dtos.requests;

public record ProdutoRequest(
        String nome,
        String descricao,
        String cor,
        Double valor,
        String modelo,
        Double peso,
        Double altura,
        Double comprimento,
        Double largura,
        String fabricante
) { }
