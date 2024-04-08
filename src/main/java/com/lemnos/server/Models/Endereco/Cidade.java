package com.lemnos.server.Models.Endereco;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cidade")
@Data
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Cidade")
    private String cidade;
}
