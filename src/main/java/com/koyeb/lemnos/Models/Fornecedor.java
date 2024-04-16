package com.koyeb.lemnos.Models;

import com.koyeb.lemnos.Annotations.CNPJValidation;
import com.koyeb.lemnos.Models.DTOs.FornecedorDTO;
import com.koyeb.lemnos.Models.Endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Column(name = "CNPJ")
    @CNPJValidation(message = "Insira um CNPJ válido")
    private String cnpj;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Telefone")
    private String telefone;

    @Column(name = "Numero_Logradouro")
    private Integer numeroLogradouro;

    @Column(name = "Email")
    @Email(message = "Insira um Email válido!")
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Funcionario")
    private List<Endereco> enderecos;

    public Fornecedor(FornecedorDTO fornecedorDTO){
        this.cnpj = fornecedorDTO.getCnpj();
        this.nome = fornecedorDTO.getNome();
        this.telefone = fornecedorDTO.getTelefone();
        this.numeroLogradouro = fornecedorDTO.getNumeroLogradouro();
        this.email = fornecedorDTO.getEmail();
    }
}
