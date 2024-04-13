package com.lemnos.server.Models.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    @NotNull
    @Positive
    private Integer cep;

    @NotBlank(message = "O campo Logradouro é obrigatório!")
    @Size(min = 3, max = 50, message = "O logradouro precisa ter no mínimo 3 caracteres e no máximo 50 caracteres!")
    private String logradouro;

    @NotBlank(message = "O campo Bairro é obrigatório!")
    @Size(min = 3, max = 40, message = "O bairro precisa ter no mínimo 3 caracteres e no máximo 40 caracteres!")
    private String bairro;

    @NotBlank(message = "O campo Cidade é obrigatório!")
    @Size(min = 3, max = 50, message = "A cidade precisa ter no mínimo 3 caracteres e no máximo 50 caracteres!")
    private String cidade;

    @NotBlank(message = "O campo Estado é obrigatório!")
    @Size(min = 2, max = 2, message = "O Estado precisa ter 2 caracteres!")
    private String uf;

}
