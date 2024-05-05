package com.lemnos.server.models.dtos.requests;

public record ClienteRequest(String nome, String cpf, String email, String senha, String situacao) {
    public ClienteRequest setNome(String nome) {
        return new ClienteRequest(nome, cpf(), email(), senha(), situacao());
    }
    public ClienteRequest setEmail(String email) {
        return new ClienteRequest(nome(), cpf(), email, senha(), situacao());
    }
    public ClienteRequest setCpf(String cpf) {
        return new ClienteRequest(nome(), cpf, email(), senha(), situacao());
    }
}
