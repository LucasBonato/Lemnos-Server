package com.lemnos.server.Models;

import com.lemnos.server.Models.DTOs.FornecedorDTO;
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
    private Integer id;

    @Column(name = "Telefone")
    private String telefone;

    @Column(name = "CNPJ", unique = true)
    private String cnpj;

    @Column(name = "CEP")
    private String cep;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Funcionario")
    private List<Endereco> enderecos;

    public Fornecedor(FornecedorDTO fornecedorDTO){
        this.telefone = fornecedorDTO.getTelefone();
        this.cnpj = fornecedorDTO.getCnpj();
        this.cep = fornecedorDTO.getCep();
    }
}
