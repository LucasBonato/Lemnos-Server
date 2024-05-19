package com.lemnos.server.models.produto.categoria;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sub_Categoria")
@NoArgsConstructor
@Data
public class SubCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nome")
    private String subCategoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Categoria")
    private Categoria categoria;

    public SubCategoria(String subCategoria){ this.subCategoria = subCategoria; }
}
