package com.lemnos.server.models.produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.entidades.Fornecedor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Data_Fornece")
public class DataFornece {
    @EmbeddedId
    @JsonIgnore
    private DataForneceId id;

    @ManyToOne
    @MapsId("id_fornecedor")
    @JoinColumn(name = "Id_Fornecedor")
    @JsonIgnore
    private Fornecedor fornecedor;

    @ManyToOne
    @MapsId("id_produto")
    @JoinColumn(name = "Id_Produto")
    @JsonIgnore
    private Produto produto;

    @CurrentTimestamp
    @Column(name = "Data_Fornecimento")
    private Date data_fornecimento;

    public DataFornece(Fornecedor fornecedor, Produto produto) {
        this.id = new DataForneceId(fornecedor.getId(), produto.getId());
        this.fornecedor = fornecedor;
        this.produto = produto;
    }
}
