package com.lemnos.server.models.produto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String nome;

    @OneToMany(mappedBy = "subCategoria")
    private List<Produto> produtos;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Categoria")
    private Categoria categoria;
}
