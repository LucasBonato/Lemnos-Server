package com.lemnos.server.models.produto;

import com.lemnos.server.models.produto.categoria.Categoria;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Descontos")
@Data
@NoArgsConstructor
public class Desconto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Valor_Porcentagem")
    private String valorDesconto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Categoria")
    private Categoria categoria;

    public Desconto(String valorDesconto){ this.valorDesconto = valorDesconto; }
}
