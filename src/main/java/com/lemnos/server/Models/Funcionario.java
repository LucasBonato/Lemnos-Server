package com.lemnos.server.Models;

import com.lemnos.server.Models.Endereco.Endereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Funcionario")
@Data
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome")
    private String Nome;

    @Column(name = "CPF", unique = true)
    private String CPF;

    @Column(name = "Data_Nascimento")
    private Date DataNascimento;

    @Column(name = "Data_Admissao")
    private Date DataAdmissao;

    @Column(name = "Telefone")
    private String Telefone;

    @Column(name = "CEP")
    private String CEP;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Funcionario_Possui_Endereco",
            joinColumns = @JoinColumn(name = "Id_Funcionario"),
            inverseJoinColumns = @JoinColumn(name = "Id_Endereco")
    )
    private List<Endereco> enderecos;
}
