package com.lemnos.server.Models;

import com.lemnos.server.Models.Endereco.Endereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Fornecedor")
@Data
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Telefone")
    private String Telefone;

    @Column(name = "CNPJ", unique = true)
    private String CNPJ;

    @Column(name = "CEP")
    private String CEP;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Funcionario")
    private List<Endereco> enderecos;
}
