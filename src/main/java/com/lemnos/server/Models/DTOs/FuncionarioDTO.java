package com.lemnos.server.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private String nome;
    private Long cpf;
    private String dataNascimento;
    private String dataAdmissao;
    private Long telefone;
    private String email;
    private String senha;
}
