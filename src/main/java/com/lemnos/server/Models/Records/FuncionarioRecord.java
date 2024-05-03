package com.lemnos.server.Models.Records;

import java.util.Date;
import java.util.List;

public record FuncionarioRecord(
        String nome,
        Long cpf,
        Date dataNascimento,
        Date dataAdmissao,
        Long telefone,
        String email,
        String senha,
        List<EnderecoRecord> enderecos
) { }