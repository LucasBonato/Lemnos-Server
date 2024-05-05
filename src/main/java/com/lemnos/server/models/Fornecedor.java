package com.lemnos.server.models;

import com.lemnos.server.annotations.CNPJ;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.enums.Situacao;
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

    public Fornecedor(FornecedorRequest fornecedorRequest){
        this.cnpj = Long.parseLong(fornecedorRequest.cnpj());
        this.nome = fornecedorRequest.nome();
        this.telefone = Long.parseLong(fornecedorRequest.telefone());
        this.numeroLogradouro = fornecedorRequest.numeroLogradouro();
        this.complemento = fornecedorRequest.complemento();
        this.email = fornecedorRequest.email();
    }
}
