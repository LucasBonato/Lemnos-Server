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

    @OneToMany(mappedBy = "imagemPrincipal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens;

    public ImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    @Override
    public String toString() {
        return "ImagemPrincipal{" +
                "id=" + id +
                ", imagemPrincipal='" + imagemPrincipal + '\'' +
                '}';
    }
}
