package com.lemnos.server.Models.Records;

public record EnderecoRecord(String cep, String logradouro, Integer numeroLogradouro, String complemento, String cidade, String bairro, String uf) { }