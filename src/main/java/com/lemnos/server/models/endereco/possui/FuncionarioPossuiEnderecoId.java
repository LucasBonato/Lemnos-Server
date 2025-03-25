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
public class FuncionarioPossuiEnderecoId implements Serializable {
    @Column(name = "id_funcionario")
    private Integer id_funcionario;
    
    @Column(name = "cep")
    private String cep;
}
