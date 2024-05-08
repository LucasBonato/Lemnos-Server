package com.lemnos.server.models;

import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "Cor")
    private String cor;

    @Column(name = "Valor")
    private Double valor;

    public Produto(ProdutoRequest produtoRequest){
        this.descricao = produtoRequest.descricao();
        this.cor = produtoRequest.descricao();
        this.valor = produtoRequest.valor();
    }
}
