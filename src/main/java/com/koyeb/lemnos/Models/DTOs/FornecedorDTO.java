package com.koyeb.lemnos.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private String nome;
    private String cnpj;
    private String cep;
    private String telefone;
    private Integer numeroLogradouro;
    private String email;
}
