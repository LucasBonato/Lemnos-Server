package com.lemnos.server.models.dtos.requests;

public record ProdutoFiltroRequest(
    String nome,
    String categoria,
    String subCategoria,
    String marca,
    Double menorPreco,
    Double maiorPreco,
    Integer page,
    Integer size,
    Double avaliacao
) { }
