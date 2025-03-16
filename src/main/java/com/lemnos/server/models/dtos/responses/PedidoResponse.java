package com.lemnos.server.models.dtos.responses;

import java.util.Date;

public record PedidoResponse(
        Integer id,
        Double valorPedido,
        String metodoPagamento,
        Date dataPedido,
        Double valorPagamento,
        Integer qtdProdutos,
        Date dataPagamento,
        String descricao,
        Double valorFrete,
        String status
) {
}
