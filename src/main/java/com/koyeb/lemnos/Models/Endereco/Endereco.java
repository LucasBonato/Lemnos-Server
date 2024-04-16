package com.koyeb.lemnos.Models.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koyeb.lemnos.Annotations.CEP;
import com.koyeb.lemnos.Models.Cliente;
import com.koyeb.lemnos.Models.Funcionario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Endereco")
@Data
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
    @PrimaryKeyJoinColumn(name = "Id_Cidade")
    private Cidade cidade;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "Id_Estado")
    private Estado estado;

    @JsonIgnore
    @Column(name = "Id_Fornecedor")
    private Integer id_Fornecedor;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Cliente> clientes;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos")
    private List<Funcionario> funcionarios;
}
