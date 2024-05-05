package com.lemnos.server.models.cadastro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
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
    @JsonIgnore
    private Integer id;

    @Column(name = "Email")
    @Email(message = "Insira um Email válido")
    private String email;

    @Column(name = "Senha")
    private String senha;

    public Cadastro(ClienteRequest clienteRequest){
        this.email = clienteRequest.email();
        this.senha = clienteRequest.senha();
    }

    public Cadastro(FuncionarioRequest funcionarioRequest){
        this.email = funcionarioRequest.email();
        this.senha = funcionarioRequest.senha();
    }
}
