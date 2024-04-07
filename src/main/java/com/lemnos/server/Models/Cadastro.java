package com.lemnos.server.Models;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cadastro")
@Data
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Senha")
    private String Senha;

    public Cadastro(ClienteDTO clienteDTO){
        this.Email = clienteDTO.getEmail();
        this.Senha = clienteDTO.getSenha();
    }
}
