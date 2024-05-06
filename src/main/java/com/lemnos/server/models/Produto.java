package com.lemnos.server.models;

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
    @Column(name = "Id_Produto")
    private Integer id;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "Cor")
    private String cor;

    @Column(name = "Valor")
    private Double valor;

    @Column(name = "Id_Imagem")
    @OneToOne(cascade = CascadeType.ALL)
    private Integer idImagem;

    @Column(name = "Id_Sub_Sub_Categoria")
    @OneToOne(cascade = CascadeType.ALL)
    private Integer idSubSubCategoria;
}
