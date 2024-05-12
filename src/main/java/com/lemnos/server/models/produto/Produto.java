package com.lemnos.server.models.produto;

import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "Cor")
    private String cor;

    @Column(name = "Valor")
    private Double valor;

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
    @JoinColumn(name = "Id_Fabricante")
    private Fabricante fabricante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Sub_Categoria")
    private SubCategoria subCategoria;

    public Produto(ProdutoRequest produtoRequest, Fabricante fabricante, SubCategoria subCategoria){
        this.nome = produtoRequest.nome();
        this.descricao = produtoRequest.descricao();
        this.cor = produtoRequest.cor();
        this.valor = produtoRequest.valor();
        this.modelo = produtoRequest.modelo();
        this.peso = produtoRequest.peso();
        this.altura = produtoRequest.altura();
        this.comprimento = produtoRequest.comprimento();
        this.largura = produtoRequest.largura();
        this.fabricante = fabricante;
        this.subCategoria = subCategoria;
    }

    public void setAll(ProdutoRequest produtoRequest, Fabricante fabricante, SubCategoria subCategoria) {
        this.nome = produtoRequest.nome();
        this.descricao = produtoRequest.descricao();
        this.cor = produtoRequest.cor();
        this.valor = produtoRequest.valor();
        this.modelo = produtoRequest.modelo();
        this.peso = produtoRequest.peso();
        this.altura = produtoRequest.altura();
        this.comprimento = produtoRequest.comprimento();
        this.largura = produtoRequest.largura();
        this.fabricante = fabricante;
        this.subCategoria = subCategoria;
    }
}
