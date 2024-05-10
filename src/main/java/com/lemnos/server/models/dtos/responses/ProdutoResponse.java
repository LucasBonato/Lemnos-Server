package com.lemnos.server.models.dtos.responses;

public record ProdutoResponse(String id, String nome,String descricao, String cor, Double valor, String modelo, Double peso, Double altura, Double comprimento, Double largura, String fabricante) {}
