package com.lemnos.server.Models.Produto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Produto")
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Descricao")
    private String Descricao;

    @Column(name = "Cor")
    private String Cor;

    @Column(name = "Valor")
    private Double Valor;

   /* @ManyToOne
    @JoinColumn(name = "Id")
    @Column(name = "Id_Itens")
    private Integer IdItensCarrinho;*/

    @ManyToOne
    @JoinColumn(name = "Id")
    @Column(name = "Id_Imagens")
    private Integer IdImagens;

    public Produto(){}

}
