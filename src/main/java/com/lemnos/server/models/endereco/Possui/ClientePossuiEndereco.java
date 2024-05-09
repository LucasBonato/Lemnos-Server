package com.lemnos.server.models.endereco.Possui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.endereco.Endereco;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cliente_Possui_Endereco")
@Data
@NoArgsConstructor
public class ClientePossuiEndereco {
    @EmbeddedId
    @JsonIgnore
    private ClientePossuiEnderecoId id;

    @ManyToOne
    @MapsId("Id_cliente")
    @JoinColumn(name = "Id_cliente")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @MapsId("Cep")
    @JoinColumn(name = "cep")
    private Endereco endereco;

    @Column(name = "numero_logradouro")
    private Integer numeroLogradouro;

    @Column(name = "Complemento")
    private String complemento;

    public ClientePossuiEndereco(Cliente cliente, Endereco endereco, Integer numeroLogradouro, String complemento) {
        this.id = new ClientePossuiEnderecoId(cliente.getId(), endereco.getCep());
        this.cliente = cliente;
        this.endereco = endereco;
        this.numeroLogradouro = numeroLogradouro;
        this.complemento = complemento;
    }
}
