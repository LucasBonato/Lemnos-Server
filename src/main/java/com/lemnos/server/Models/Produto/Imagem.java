package com.lemnos.server.Models.Produto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Imagem")
@Data
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Imagem_Principal")
    private String imagemPrincipal;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Imagens")
    private List<Imagens> imagens;
}
