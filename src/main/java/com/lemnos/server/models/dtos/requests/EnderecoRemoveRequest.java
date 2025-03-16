package com.lemnos.server.models.dtos.requests;

import com.lemnos.server.models.enums.Entidade;

public record EnderecoRemoveRequest(
        String email,
        String cep,
        Entidade entidade
) {}
