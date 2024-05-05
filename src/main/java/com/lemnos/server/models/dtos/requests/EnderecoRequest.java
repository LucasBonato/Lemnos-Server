package com.lemnos.server.models.dtos.requests;

public record EnderecoRequest(String cep, String logradouro, Integer numeroLogradouro, String complemento, String bairro, String cidade, String uf) {
    public EnderecoRequest setBairro(String bairro) {
        return new EnderecoRequest(cep(), logradouro(), numeroLogradouro(), complemento(), bairro, cidade(), uf());
    }
    public EnderecoRequest setCidade(String cidade) {
        return new EnderecoRequest(cep(), logradouro(), numeroLogradouro(), complemento(), bairro(), cidade, uf());
    }
    public EnderecoRequest setUf(String uf) {
        return new EnderecoRequest(cep(), logradouro(), numeroLogradouro(), complemento(), bairro(), cidade(), uf);
    }
}