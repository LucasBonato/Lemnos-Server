package com.lemnos.server.Models.DTOs;

import com.lemnos.server.Annotations.CPFValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    @NotBlank(message = "O campo Nome é obrigatório!")
    @Size(min = 3, max = 40, message = "O nome precisa ter no mínimo 3 caracteres e no máximo 40 caracteres!")
    private String nome;

    @NotBlank(message = "O campo CPF é obrigatório!")
    @CPFValidation(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O campo Data de Nascimento é obrigatório!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dataNascimento;

    @NotBlank(message = "O campo Data de Admissão é obrigatório!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dataAdmissao;

    @NotBlank(message = "O campo Telefone é obrigatório!")
    @Size(min = 11, max = 11, message = "O telefone precisa ter 11 caracteres!")
    private String telefone;

    @NotBlank(message = "O campo CEP é obrigatório!")
    @Size(min = 8, max = 8, message = "O CEP precisa ter 8 caracteres!")
    private String cep;

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
