package com.lemnos.server.models.pedido;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

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

    @CurrentTimestamp
    @Column(name = "Data_Pedido")
    private Date dataPedido;

    @Column(name = "Valor_Pagamento")
    private Double valorPagamento;

    @Column(name = "Quantidade_Produtos")
    private Integer qntdProdutos;

    @CurrentTimestamp
    @Column(name = "Data_Pagamento")
    private Date dataPagamento;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "Status")
    private String status = Status.EM_PROCESSAMENTO.getStatus();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    public Pedido(Double valorPedido, String metodoPagamento, Double valorPagamento, Integer qntdProdutos, String descricao, Cadastro cadastro) {
        this.valorPedido = valorPedido;
        this.metodoPagamento = metodoPagamento;
        this.valorPagamento = valorPagamento;
        this.qntdProdutos = qntdProdutos;
        this.descricao = descricao;
        this.cadastro = cadastro;
    }
}
