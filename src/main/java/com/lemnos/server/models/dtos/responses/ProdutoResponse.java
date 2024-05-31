package com.lemnos.server.models.dtos.responses;

import java.util.List;

public record ProdutoResponse(
        String id,
        String nome,
        String descricao,
        String cor,
        Double valorTotal,
        Double valorComDesconto,
        String modelo,
        Double peso,
        Double altura,
        Double comprimento,
        Double largura,
        String fabricante,
        String categoria,
        String subCategoria,
        String imagemPrincipal,
        List<String> imagens,
        String desconto,
        Double avaliacao,
        Integer quantidadeAvaliacoes
) {}
