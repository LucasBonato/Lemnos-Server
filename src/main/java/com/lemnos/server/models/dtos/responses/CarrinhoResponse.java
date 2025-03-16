package com.lemnos.server.models.dtos.responses;

import java.util.List;

public record CarrinhoResponse(
        Integer qntdTotalProdutos,
        Double valorTotal,
        List<ItemCarrinhoResponse> produtos
) { }
