package com.lemnos.server.models.endereco.possui;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientePossuiEnderecoId implements Serializable {
    @Column(name = "id_cliente")
    private Integer id_cliente;
    
    @Column(name = "cep")
    private String cep;
}
