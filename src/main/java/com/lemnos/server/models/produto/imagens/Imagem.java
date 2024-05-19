package com.lemnos.server.models.produto.imagens;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.dtos.requests.ImagemRequest;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Id_Imagem")
    @JsonIgnore
    private ImagemPrincipal imagemPrincipal;

    public Imagem(ImagemRequest imagemRequest, ImagemPrincipal imagemPrincipal) {
        this.imagem = imagemRequest.imagem();
        this.imagemPrincipal = imagemPrincipal;
    }
}
