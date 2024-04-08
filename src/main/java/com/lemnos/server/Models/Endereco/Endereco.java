package com.lemnos.server.Models.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.Funcionario;
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

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "Id_Cidade")
    private Cidade cidade;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "Id_Estado")
    private Estado estado;

    @Column(name = "Id_Fornecedor")
    private Integer Id_Fornecedor;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Cliente> clientes;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Funcionario> funcionarios;
}