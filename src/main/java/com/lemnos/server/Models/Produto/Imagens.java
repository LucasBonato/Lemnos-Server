package com.lemnos.server.Models.Produto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Imagens")
@Data
public class Imagens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "Imagens")
    private String Imagens;

    @ManyToOne
    @JoinColumn(name = "Id")
    @Column(name = "Id_Imagem")
    private Integer IdImagem;

    public Imagens(){}
}
