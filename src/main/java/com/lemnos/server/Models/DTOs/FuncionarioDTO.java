package com.lemnos.server.Models.DTOs;

import com.lemnos.server.Annotations.CPFValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 50, message = "O nome precisa ter no mínimo 3 carateres e no máximo 50 carateres!")
    private String nome;

    @NotBlank(message = "O campo CPF é obrigatório!")
    @CPFValidation(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O campo Data de Nascimento é obrigatório!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    @NotBlank(message = "O campo Data de Admissão é obrigatório!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataAdmissao;

    @NotBlank(message = "O campo Telefone é obrigatório!")
    @Size(min = 11, max = 11, message = "O telefone precisa ter 11 carateres!")
    private char telefone;

    @NotBlank(message = "O campo CEP é obrigatório!")
    @Size(min = 8, max = 8, message = "O CEP precisa ter 8 carateres!")
    private char cep;
}
