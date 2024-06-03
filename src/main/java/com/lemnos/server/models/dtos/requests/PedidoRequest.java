package com.lemnos.server.models.dtos.requests;


public record PedidoRequest(
        Double valorPedido,
        String metodoPagamento,
        Double valorPagamento,
        Integer qtdProdutos,
        String descricao
) {
}
