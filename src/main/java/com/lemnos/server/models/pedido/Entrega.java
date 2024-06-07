package com.lemnos.server.models.pedido;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Entrega")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CurrentTimestamp
    @Column(name = "data_entrega")
    private Date dataEntrega;

    @Column(name = "status_entrega")
    private String status = "Entregue";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Pedido")
    private Pedido pedido;

    public Entrega(Pedido pedido) {
        this.pedido = pedido;
    }
}
