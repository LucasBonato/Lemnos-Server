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
    private Integer Id;

    @Column(name = "Valor_Pedido")
    private Double Valor_Pedido;

    @Column(name = "Metodo_Pagamento")
    private String Metodo_Pagamento;

    @Column(name = "Data_Pedido")
    private Date Data_Pedido;

    @Column(name = "Valor_Pagamento")
    private Double Valor_Pagamento;

    @Column(name = "Quantidade_Produtos")
    private Integer Quantidade_Prdoutos;

    @Column(name = "Data_Pagamento")
    private Date Data_Pagamento;

    public Pedido(){}
}
