package com.lemnos.server.Models.Records;

import java.util.List;

public record ClienteRecord(String nome, Long Cpf, String email, String senha, List<EnderecoRecord> enderecos) { }
