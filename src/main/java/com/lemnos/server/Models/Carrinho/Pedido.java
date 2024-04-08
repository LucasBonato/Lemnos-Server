package com.lemnos.server.Models.Carrinho;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Pedido")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Valor_Pedido")
    private Double valor_Pedido;

    @Column(name = "Metodo_Pagamento")
    private String metodo_Pagamento;

    @Column(name = "Data_Pedido")
    private Date data_Pedido;

    @Column(name = "Valor_Pagamento")
    private Double valor_Pagamento;

    @Column(name = "Quantidade_Produtos")
    private Integer quantidade_Prdoutos;

    @Column(name = "Data_Pagamento")
    private Date data_Pagamento;
}
