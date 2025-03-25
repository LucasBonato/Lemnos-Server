package com.lemnos.server.models.endereco.possui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.entidades.Cliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente_possui_endereco")
@Getter
@Setter
@NoArgsConstructor
public class ClientePossuiEndereco {
    @EmbeddedId
    @JsonIgnore
    private ClientePossuiEnderecoId id;
    
    @ManyToOne
    @MapsId("id_cliente")
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnore
    private Cliente cliente;
    
    @ManyToOne
    @MapsId("cep")
    @JoinColumn(name = "cep", nullable = false)
    @JsonIgnore
    private Endereco endereco;
    
    @Column(name = "numero_logradouro")
    private Integer numeroLogradouro;
    
    @Column(name = "complemento")
    private String complemento;
    
    public ClientePossuiEndereco(Cliente cliente, Endereco endereco, Integer numeroLogradouro, String complemento) {
        this.id = new ClientePossuiEnderecoId(cliente.getId(), endereco.getCep());
        this.cliente = cliente;
        this.numeroLogradouro = numeroLogradouro;
        this.complemento = complemento;
    }
}
