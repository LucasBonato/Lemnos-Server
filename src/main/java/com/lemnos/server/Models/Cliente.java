package com.lemnos.server.Models;

import com.lemnos.server.Annotations.CPFValidation;
import com.lemnos.server.Models.Cadastro.Cadastro;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.Endereco.Endereco;
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
    @CPFValidation(message = "CPF preenchido incorretamente!")
    private Long cpf;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Cliente_Possui_Endereco",
            joinColumns = @JoinColumn(name = "Id_Cliente"),
            inverseJoinColumns = @JoinColumn(name = "CEP")
    )
    private List<Endereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

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
