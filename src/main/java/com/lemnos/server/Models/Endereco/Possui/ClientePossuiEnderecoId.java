package com.lemnos.server.Models.Endereco.Possui;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ClientePossuiEnderecoId implements Serializable {
    @Column(name = "id_cliente")
    private Integer Id_cliente;

    @Column(name = "cep")
    private String Cep;

    public ClientePossuiEnderecoId(Integer id, String cep) {
        this.Id_cliente = id;
        this.Cep = cep;
    }
}
