package com.lemnos.server.models.dtos.requests;

public record EnderecoRequest(
        Integer id,
        String cep,
        Integer numeroLogradouro,
        String complemento,
        String entidade
) { }