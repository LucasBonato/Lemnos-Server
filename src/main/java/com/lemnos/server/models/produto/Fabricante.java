package com.lemnos.server.models.produto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Fabricante")
public class Fabricante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Fabricante")
    private String fabricante;

    public Fabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
