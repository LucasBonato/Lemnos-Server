package com.lemnos.server.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Endereco")
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Logradouro")
    private String Logradouro;

    @Column(name = "Numero_Logradouro")
    private Integer NumeroLogradouro ;

    @Column(name = "Bairro")
    private String Bairro;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Cliente> clientes;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Funcionario> funcionarios;
}
