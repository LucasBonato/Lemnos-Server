package com.lemnos.server.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private String nome;
    private Long cnpj;
    private Long telefone;
    private Integer numeroLogradouro;
    private String email;
}
