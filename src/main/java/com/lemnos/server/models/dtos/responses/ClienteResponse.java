package com.lemnos.server.models.dtos.responses;

import java.util.List;

public record ClienteResponse(
        String nome,
        String email,
        String situacao,
        String cpf,
        List<String> produtosFavoritos,
        List<EnderecoResponse> enderecos
) { }
