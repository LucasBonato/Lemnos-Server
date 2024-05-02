package com.lemnos.server.Models;

import com.lemnos.server.Annotations.CPF;
import com.lemnos.server.Models.Cadastro.Cadastro;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Enums.Situacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF")
    @CPF(message = "CPF preenchido incorretamente!")
    private Long cpf;

    @OneToMany(mappedBy = "cliente")
    private List<ClientePossuiEndereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    public Cliente(ClienteDTO clienteDTO){
        this.nome = clienteDTO.getNome();
        this.cpf = Long.parseLong(clienteDTO.getCpf());
        this.cadastro = new Cadastro(clienteDTO);
    }
    public Cliente(String nome, Long cpf){
        this.nome = nome;
        this.cpf = cpf;
    }
}
