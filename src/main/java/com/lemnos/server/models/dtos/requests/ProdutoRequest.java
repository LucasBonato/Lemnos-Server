package com.lemnos.server.models.dtos.requests;

import java.util.List;

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
        String fabricante,
        String fornecedor,
        String subCategoria,
        String imagemPrincipal,
        List<String> imagens,
        String desconto
) { }
