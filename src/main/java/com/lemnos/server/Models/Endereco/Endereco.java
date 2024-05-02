package com.lemnos.server.Models.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.Annotations.CEP;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Funcionario;
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
    @Column(name = "Id_Fornecedor")
    private Integer id_Fornecedor;

    @JsonIgnore
    @OneToMany(mappedBy = "endereco")
    private List<ClientePossuiEndereco> clientes;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Funcionario> funcionarios;

    public Endereco(String cep, Cidade cidade, Estado estado, EnderecoDTO enderecoDTO){
        this.cep = cep;
        this.logradouro = enderecoDTO.getLogradouro();
        this.bairro = enderecoDTO.getBairro();
        this.cidade = cidade;
        this.estado = estado;
    }
}
