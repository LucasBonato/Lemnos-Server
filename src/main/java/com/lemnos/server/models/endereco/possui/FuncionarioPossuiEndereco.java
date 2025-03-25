package com.lemnos.server.models.endereco.possui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.entidades.Funcionario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionario_possui_endereco")
@NoArgsConstructor
@Getter
@Setter
public class FuncionarioPossuiEndereco {
    @EmbeddedId
    private FuncionarioPossuiEnderecoId id;
    
    @ManyToOne
    @MapsId("id_funcionario")
    @JoinColumn(name = "id_funcionario", nullable = false)
    @JsonIgnore
    private Funcionario funcionario;
    
    @ManyToOne
    @MapsId("cep")
    @JoinColumn(name = "cep", nullable = false)
    @JsonIgnore
    private Endereco endereco;
    
    @Column(name = "numero_logradouro")
    private Integer numeroLogradouro;
    
    @Column(name = "complemento")
    private String complemento;
    
    public FuncionarioPossuiEndereco(Funcionario funcionario, Endereco endereco, Integer numeroLogradouro, String complemento) {
        this.id = new FuncionarioPossuiEnderecoId(funcionario.getId(), endereco.getCep());
        this.funcionario = funcionario;
        this.endereco = endereco;
        this.numeroLogradouro = numeroLogradouro;
        this.complemento = complemento;
    }
}
