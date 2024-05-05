package com.lemnos.server.models.dtos.requests;

public record EnderecoRequest(String cep, Integer numeroLogradouro, String complemento) { }