package com.lemnos.server.Models.DTOs;

import com.lemnos.server.Annotations.CPFValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    @NotBlank(message = "O Nome é obrigatório!")
    @Size(min = 3, max = 40, message = "O nome precisa ter no mínimo 3 carateres e no máximo 40 carateres!")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório!")
    @CPFValidation(message = "CPF preenchido incorretamente!")
    private String cpf;

    @NotBlank(message = "O Email é obrigatório!")
    @Email(message = "Insira um Email válido!")
    private String email;

    @NotBlank(message = "A Senha é obrigatória!")
    @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres!")
    private String senha;

    @NotNull(message = "O campo 'N Logradouro' é obrigatório!")
    @PositiveOrZero(message = "Digite um número válido")
    @Max(value = 9999, message = "O número inserido é muito alto!")
    private Integer numeroLogradouro;
}
