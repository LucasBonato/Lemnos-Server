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
    private Integer id;

    @Column(name = "Valor")
    private Double valor;

    @Column(name = "Quantidade_Produtos")
    private Integer quantidadeProdutos;

    @OneToOne
    @JoinColumn(name = "Id_Cadastro")
    private Integer idCadastro;
}
