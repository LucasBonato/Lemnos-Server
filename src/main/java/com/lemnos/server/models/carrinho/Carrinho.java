package com.lemnos.server.models.carrinho;

import com.lemnos.server.models.cadastro.Cadastro;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Carrinho")
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Valor")
    private Double valor;

    @Column(name = "quantidade_produtos")
    private Integer quantidadeProdutos;

    @ManyToOne
    @JoinColumn(name = "id_cadastro")
    private Cadastro cadastro;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensCarrinho> itens;

    public Carrinho(Cadastro cadastro) {
        this.cadastro = cadastro;
    }

    public void setAll(List<ItensCarrinho> itens, Double valorTotal, Integer quantidadeProdutos) {
        this.itens = itens;
        this.valor = valorTotal;
        this.quantidadeProdutos = quantidadeProdutos;
    }
}
