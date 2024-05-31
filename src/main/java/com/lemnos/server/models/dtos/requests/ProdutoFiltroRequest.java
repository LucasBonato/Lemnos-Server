package com.lemnos.server.models.dtos.requests;

public record ProdutoFiltroRequest(
    String categoria,
    String subCategoria,
    String marca,
    Double menorPreco,
    Double maiorPreco
) { }
