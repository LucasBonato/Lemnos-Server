package com.lemnos.server.Models;

import com.lemnos.server.Annotations.CNPJValidation;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Enums.Situacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Fornecedor")
@Data
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Fornecedor")
    private List<Endereco> enderecos;

    public Fornecedor(FornecedorDTO fornecedorDTO){
        this.cnpj = fornecedorDTO.getCnpj();
        this.nome = fornecedorDTO.getNome();
        this.telefone = fornecedorDTO.getTelefone();
        this.numeroLogradouro = fornecedorDTO.getNumeroLogradouro();
        this.email = fornecedorDTO.getEmail();
    }
}
