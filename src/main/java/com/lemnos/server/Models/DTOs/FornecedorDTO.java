package com.lemnos.server.Models.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O Email é obrigatório!")
    @Email(message = "Insira um Email válido!")
    private String email;
}
