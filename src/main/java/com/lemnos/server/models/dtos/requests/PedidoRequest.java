package com.lemnos.server.models.dtos.requests;


public record PedidoRequest(
        String metodoPagamento,
        Double valorPagamento,
        Double valorFrete
) {
}
