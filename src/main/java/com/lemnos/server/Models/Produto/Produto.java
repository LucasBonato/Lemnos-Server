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
    private Integer id;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "Cor")
    private String cor;

    @Column(name = "Valor")
    private Double valor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Imagem")
    private Imagem imagem;
}
