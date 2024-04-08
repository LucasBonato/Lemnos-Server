package com.lemnos.server.Models.Produto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Avaliacao")
@Data
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Data_Avaliacao")
    private Date dataAvaliacao;

    @Column(name = "Avaliacao")
    private Double avaliacao;

    @ManyToOne
    @JoinColumn(name = "Id")
    private Integer idProduto;
}
