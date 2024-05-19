package com.lemnos.server.models.produto.imagens;

import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Imagens")
@NoArgsConstructor
public class Imagens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Imagem")
    private String imagens;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Imagem")
    private ImagemPrincipal imagemPrincipal;

    public Imagens(ImagemPrincipal imagemPrincipal){
        this.imagemPrincipal = imagemPrincipal;
    }
}
