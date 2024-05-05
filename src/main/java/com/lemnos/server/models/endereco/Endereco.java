package com.lemnos.server.models.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.annotations.CEP;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.viacep.ViaCepDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Endereco")
@Data
@NoArgsConstructor
public class Endereco {
    @Id
    @Column(name = "CEP")
    @CEP(message = "CEP Inválido! (XXXXX-XXX)")
    private String cep;

    @Column(name = "Logradouro")
    private String logradouro;

    @Column(name = "Bairro")
    private String bairro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cidade")
    private Cidade cidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado")
    private Estado estado;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco")
    private List<ClientePossuiEndereco> clientes;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco")
    private List<FuncionarioPossuiEndereco> funcionarios;

    public Endereco(ViaCepDTO viaCepDTO, Cidade cidade, Estado estado) {
        this.cep = viaCepDTO.cep();
        this.logradouro = viaCepDTO.logradouro();
        this.bairro = viaCepDTO.bairro();
        this.cidade = cidade;
        this.estado = estado;
    }
}
