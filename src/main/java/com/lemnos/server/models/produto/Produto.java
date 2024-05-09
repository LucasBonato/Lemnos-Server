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

    public Produto(ProdutoRequest produtoRequest){
        this.nome = produtoRequest.nome();
        this.descricao = produtoRequest.descricao();
        this.cor = produtoRequest.cor();
        this.valor = produtoRequest.valor();
    }
}
