package com.lemnos.server.Models.Endereco.Possui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.Endereco.Endereco;
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

    public ClientePossuiEndereco(Cliente cliente, Endereco endereco, Integer numeroLogradouro) {
        this.id = new ClientePossuiEnderecoId(cliente.getId(), endereco.getCep());
        this.cliente = cliente;
        this.endereco = endereco;
        this.numeroLogradouro = numeroLogradouro;
    }
}
