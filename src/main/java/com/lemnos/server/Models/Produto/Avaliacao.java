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
    private Integer Id;

    @Column(name = "Data_Avaliacao")
    private Date DataAvaliacao;

    @Column(name = "Avaliacao")
    private Double Avaliacao;

    @ManyToOne
    @JoinColumn(name = "Id")
    private Integer IdProduto;

    public Avaliacao(){}
}
