package com.lemnos.server.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String dataAdmissao;
    private String telefone;
    private String cep;
    private String email;
    private String senha;
    private Integer numeroLogradouro;
}
