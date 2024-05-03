package com.lemnos.server.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private String nome;
    private String cnpj;
    private String telefone;
    private Integer numeroLogradouro;
    private String complemento;
    private String email;
    private String situacao;
}
