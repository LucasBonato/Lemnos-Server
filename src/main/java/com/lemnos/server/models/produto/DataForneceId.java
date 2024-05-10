package com.lemnos.server.models.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
public class DataForneceId implements Serializable {
    @Column(name = "Id_Fornecedor")
    private Integer id_fornecedor;

    @Column(name = "Id_Produto")
    private UUID id_produto;

    public DataForneceId(Integer id_fornecedor, UUID id_produto) {
        this.id_fornecedor = id_fornecedor;
        this.id_produto = id_produto;
    }
}
