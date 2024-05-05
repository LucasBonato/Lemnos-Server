package com.lemnos.server.models.dtos.requests;

public record FornecedorRequest(String nome, String cnpj, String telefone, Integer numeroLogradouro, String complemento, String email, String situacao) {
    public FornecedorRequest setNome(String nome) {
        return new FornecedorRequest(nome, cnpj(), telefone(), numeroLogradouro(), complemento(), email(), situacao());
    }
    public FornecedorRequest setCnpj(String cnpj) {
        return new FornecedorRequest(nome(), cnpj, telefone(), numeroLogradouro(), complemento(), email(), situacao());
    }
    public FornecedorRequest setTelefone(String telefone) {
        return new FornecedorRequest(nome(), cnpj(), telefone, numeroLogradouro(), complemento(), email(), situacao());
    }
}