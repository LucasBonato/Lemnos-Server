package com.lemnos.server.models.dtos.requests.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String situacao;
}
