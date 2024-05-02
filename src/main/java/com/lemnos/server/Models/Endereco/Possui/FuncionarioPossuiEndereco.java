package com.lemnos.server.Models.Endereco.Possui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Funcionario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Funcionario_Possui_Endereco")
@Data
@NoArgsConstructor
public class FuncionarioPossuiEndereco {
    @EmbeddedId
    @JsonIgnore
    private FuncionarioPossuiEnderecoId id;

    @ManyToOne
    @MapsId("id_funcionario")
    @JoinColumn(name = "id_funcionario")
    @JsonIgnore
    private Funcionario funcionario;

    @ManyToOne
    @MapsId("Cep")
    @JoinColumn(name = "cep")
    private Endereco endereco;

    @Column(name = "numero_logradouro")
    private Integer numeroLogradouro;

    public FuncionarioPossuiEndereco(Funcionario funcionario, Endereco endereco, Integer numeroLogradouro) {
        this.id = new FuncionarioPossuiEnderecoId(funcionario.getId(), endereco.getCep());
        this.funcionario = funcionario;
        this.endereco = endereco;
        this.numeroLogradouro = numeroLogradouro;
    }
}
