package com.lemnos.server.models.produto.imagens;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Imagens")
@NoArgsConstructor
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Imagem")
    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Imagem")
    @JsonIgnore
    private ImagemPrincipal imagemPrincipal;

    public Imagem(String imagem, ImagemPrincipal imagemPrincipal) {
        this.imagem = imagem;
        this.imagemPrincipal = imagemPrincipal;
    }

    @Override
    public String toString() {
        return "Imagem{" +
                "id=" + id +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}
