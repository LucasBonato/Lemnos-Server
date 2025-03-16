package com.lemnos.server.models.produto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Data
@Table(name = "Avaliacao")
@NoArgsConstructor
@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @CurrentTimestamp
    @Column(name = "Data_Avaliacao")
    private Date dataAvaliacao;

    @Column(name = "Avaliacao")
    private Double avaliacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Produto", nullable = false)
    private Produto produto;

    public Avaliacao(Produto produto, Double avaliacao) {
        this.produto = produto;
        this.avaliacao = avaliacao;
    }
}
