package com.lemnos.server.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private Integer numeroLogradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
