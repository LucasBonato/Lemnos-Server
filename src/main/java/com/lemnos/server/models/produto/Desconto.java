package com.lemnos.server.models.produto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Desconto")
@Data
@NoArgsConstructor
public class Desconto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Valor_Porcentagem")
    private String valorDesconto;
}
