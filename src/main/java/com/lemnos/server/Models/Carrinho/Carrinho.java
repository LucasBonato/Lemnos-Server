package com.lemnos.server.Models.Carrinho;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Carrinho")
@Data
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Valor")
    private Double Valor;

    @Column(name = "Quantidade_Produtos")
    private Integer QuantidadeProdutos;

    @OneToOne
    @JoinColumn(name = "Id_Cadastro")
    private Integer IdCadastro;

    public Carrinho(){}
}
