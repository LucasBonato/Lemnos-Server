package com.lemnos.server.models.dtos.requests;


public record PedidoRequest(
        String email,
        String metodoPagamento,
        Double valorPagamento
) {
}
