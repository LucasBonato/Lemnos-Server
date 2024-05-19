package com.lemnos.server.models.produto.imagens;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Imagem")
@NoArgsConstructor
public class ImagemPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Imagem_Principal")
    private String imagemPrincipal;
}
