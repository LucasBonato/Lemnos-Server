package com.lemnos.server.models.produto;

import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Especificacao")
public class Especificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Modelo")
    private String modelo;

    @Column(name = "Peso")
    private Double peso;

    @Column(name = "Altura")
    private Double altura;

    @Column(name = "Comprimento")
    private Double comprimento;

    @Column(name = "Largura")
    private Double largura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id")
    private Fabricante fabricante;

    public Especificacao(ProdutoRequest produtoRequest, Fabricante fabricante) {
        this.modelo = produtoRequest.modelo();
        this.peso = produtoRequest.peso();
        this.altura = produtoRequest.altura();
        this.comprimento = produtoRequest.comprimento();
        this.largura = produtoRequest.largura();
        this.fabricante = fabricante;
    }
}
