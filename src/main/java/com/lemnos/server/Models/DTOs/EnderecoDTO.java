package com.lemnos.server.Models.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    @NotBlank(message = "O campo Logradouro é obrigatório!")
    @Size(min = 3, max = 50, message = "O logradouro precisa ter no mínimo 3 carateres e no máximo 50 carateres!")
    private String logradouro;

    @NotBlank(message = "O campo N° do Logradouro é obrigatório!")
    @Size(min = 1, max = 4, message = "O N° do logradouro precisa ter no mínimo 3 caracteres e no máximo 4 caracteres!")
    private Integer numLogradouro;

    @NotBlank(message = "O campo Bairro é obrigatório!")
    @Size(min = 3, max = 40, message = "O bairro precisa ter no mínimo 3 carateres e no máximo 40 carateres!")
    private String bairro;

    @NotBlank(message = "O campo Cidade é obrigatório!")
    @Size(min = 3, max = 50, message = "A cidade precisa ter no mínimo 3 carateres e no máximo 50 carateres!")
    private String cidade;

    @NotBlank(message = "O campo Estado é obrigatório!")
    @Size(min = 2, max = 2, message = "O Estado precisa ter 2 caracteres!")
    private char uf;

}
