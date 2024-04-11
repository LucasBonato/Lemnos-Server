package com.lemnos.server.Models.Produto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Imagens")
@Data
public class Imagens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Imagens")
    private String imagens;
}
