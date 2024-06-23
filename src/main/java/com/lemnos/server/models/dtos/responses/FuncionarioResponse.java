package com.lemnos.server.models.dtos.responses;

import java.util.Date;
import java.util.List;

public record FuncionarioResponse(
        String nome,
        Date dataNascimento,
        Date dataAdmissao,
        Long telefone,
        Long cpf,
        String email,
        String situacao,
        List<EnderecoResponse> enderecos
) { }