package com.lemnos.server.Models.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {

    @NotBlank(message = "O campo Telefone é obrigatório!")
    @Size(min = 11, max = 11, message = "O telefone precisa ter 11 caracteres!")
    private String telefone;

    @NotBlank(message = "O campo CNPJ é obrigatório!")
    @Size(min = 14, max = 14, message = "O CNPJ precisa ter 14 caracteres!")
    private String cnpj;

    @NotBlank(message = "O campo CEP é obrigatório!")
    @Size(min = 8, max = 8, message = "O CEP precisa ter 8 caracteres!")
    private String cep;

    @NotNull(message = "O campo 'N Logradouro' é obrigatório!")
    @PositiveOrZero(message = "Digite um número válido")
    @Max(value = 9999, message = "O número inserido é muito alto!")
    private Integer numeroLogradouro;
}
