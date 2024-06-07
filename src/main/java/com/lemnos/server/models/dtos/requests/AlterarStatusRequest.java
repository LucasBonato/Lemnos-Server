package com.lemnos.server.models.dtos.requests;

public record AlterarStatusRequest(
        String email,
        Integer id
) { }
