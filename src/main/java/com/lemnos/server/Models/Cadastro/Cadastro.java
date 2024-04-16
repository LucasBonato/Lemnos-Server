package com.lemnos.server.Models.Cadastro;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cadastro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Email")
    @Email(message = "Insira um Email válido")
    private String email;

    @Column(name = "Senha")
    private String senha;

    public Cadastro(ClienteDTO clienteDTO){
        this.email = clienteDTO.getEmail();
        this.senha = clienteDTO.getSenha();
    }

    public Cadastro(FuncionarioDTO funcionarioDTO){
        this.email = funcionarioDTO.getEmail();
        this.senha = funcionarioDTO.getSenha();
    }
}
