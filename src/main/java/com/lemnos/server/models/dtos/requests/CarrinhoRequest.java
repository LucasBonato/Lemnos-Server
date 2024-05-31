package com.lemnos.server.models.dtos.requests;

public record CarrinhoRequest(
    String id,
    String email,
    Integer quantidade
) { }
