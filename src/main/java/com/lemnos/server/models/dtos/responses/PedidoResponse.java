package com.lemnos.server.models.dtos.responses;

import java.util.Date;

public record PedidoResponse(
        Double valorPedido,
        String metodoPagamento,
        Date dataPedido,
        Double valorPagamento,
        Integer qtdProdutos,
        Date dataPagamento,
        String descricao
) {
}
