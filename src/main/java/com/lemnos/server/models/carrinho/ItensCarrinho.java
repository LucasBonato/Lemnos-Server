package com.lemnos.server.models.carrinho;

import com.lemnos.server.models.produto.Produto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Itens_Carrinho")
public class ItensCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_carrinho")
    private Carrinho carrinho;

    public ItensCarrinho(Carrinho carrinho, Produto produto, Integer quantidade) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.carrinho = carrinho;
    }
}
