package com.lemnos.server.Models;

import com.lemnos.server.Annotations.CNPJ;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Enums.Situacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @CNPJ(message = "Insira um CNPJ válido")
    private Long cnpj;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Telefone")
    private Long telefone;

    @Column(name = "Numero_Logradouro")
    private Integer numeroLogradouro;

    @Column(name = "Complemento")
    private String complemento;

    @Column(name = "Email")
    @Email(message = "Insira um Email válido!")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CEP")
    private Endereco endereco;

    public Fornecedor(FornecedorDTO fornecedorDTO){
        this.cnpj = Long.parseLong(fornecedorDTO.getCnpj());
        this.nome = fornecedorDTO.getNome();
        this.telefone = Long.parseLong(fornecedorDTO.getTelefone());
        this.numeroLogradouro = fornecedorDTO.getNumeroLogradouro();
        this.complemento = fornecedorDTO.getComplemento();
        this.email = fornecedorDTO.getEmail();
    }
}
