package com.lemnos.server.models.endereco.possui;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class FuncionarioPossuiEnderecoId implements Serializable {
    @Column(name = "id_funcionario")
    private Integer id_funcionario;

    @Column(name = "cep")
    private String Cep;

    public FuncionarioPossuiEnderecoId(Integer id, String cep) {
        this.id_funcionario = id;
        this.Cep = cep;
    }
}
