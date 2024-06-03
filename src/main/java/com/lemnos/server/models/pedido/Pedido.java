package com.lemnos.server.models.pedido;

import com.lemnos.server.models.carrinho.Carrinho;
import com.lemnos.server.models.dtos.requests.PedidoRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "Pedido")
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Valor_Pedido")
    private Double valorPedido;

    @Column(name = "Metodo_Pagamento")
    private String metodoPagamento;

    @Column(name = "Data_Pedido")
    private Date dataPedido;

    @Column(name = "Valor_Pagamento")
    private Double valorPagamento;

    @Column(name = "Quantidade_Produtos")
    private Integer qtdProdutos;

    @Column(name = "Data_Pagamento")
    private Date dataPagamento;

    @Column(name = "Descricao")
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Carrinho")
    private Carrinho carrinho;

    public Pedido(PedidoRequest pedidoRequest, Date dataPedido, Date dataPagamento, Carrinho carrinho){
        this.valorPedido = pedidoRequest.valorPedido();
        this.metodoPagamento = pedidoRequest.metodoPagamento();
        this.dataPedido = dataPedido;
        this.valorPagamento = pedidoRequest.valorPagamento();
        this.qtdProdutos = pedidoRequest.qtdProdutos();
        this.dataPagamento = dataPagamento;
        this.descricao = pedidoRequest.descricao();
        this.carrinho = carrinho;
    }
}
