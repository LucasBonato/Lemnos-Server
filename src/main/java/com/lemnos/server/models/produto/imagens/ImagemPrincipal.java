package com.lemnos.server.models.produto.imagens;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "imagemPrincipal")
    private List<Imagens> imagens;
}
