package com.lemnos.server.models.dtos.requests;

public record FuncionarioRequest(String nome, String cpf, String dataNascimento, String dataAdmissao, String telefone, String email, String senha) {
    public FuncionarioRequest setNome(String nome) {
        return new FuncionarioRequest(nome, cpf(), dataNascimento(), dataAdmissao(), telefone(), email(), senha());
    }
    public FuncionarioRequest setCpf(String cpf) {
        return new FuncionarioRequest(nome(), cpf, dataNascimento(), dataAdmissao(), telefone(), email(), senha());
    }
    public FuncionarioRequest setTelefone(String telefone) {
        return new FuncionarioRequest(nome(), cpf(), dataNascimento(), dataAdmissao(), telefone, email(), senha());
    }
    public FuncionarioRequest setEmail(String email) {
        return new FuncionarioRequest(nome(), cpf(), dataNascimento(), dataAdmissao(), telefone(), email, senha());
    }
}