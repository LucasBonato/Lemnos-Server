package com.lemnos.server.Models.Records;

public record FornecedorRecord(
        String nome,
        Long cnpj,
        Long telefone,
        String email,
        EnderecoRecord endereco
) { }