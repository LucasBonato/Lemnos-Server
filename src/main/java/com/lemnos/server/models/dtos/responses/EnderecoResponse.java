package com.lemnos.server.models.dtos.responses;

public record EnderecoResponse(String cep, String logradouro, Integer numeroLogradouro, String complemento, String cidade, String bairro, String uf) { }