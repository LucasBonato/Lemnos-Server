package com.lemnos.server.models.dtos.requests;

public record EnderecoRequest(
        String email,
        String cep,
        Integer numeroLogradouro,
        String complemento,
        String entidade
) { }