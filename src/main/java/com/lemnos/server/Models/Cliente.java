package com.lemnos.server.Models;

import com.lemnos.server.Annotations.CPFValidation;
import com.lemnos.server.Models.Cadastro.Cadastro;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    private String cpf;

    @Column(name = "Numero_Logradouro")
    @Null
    @PositiveOrZero(message = "Digite um número válido")
    @Max(value = 9999, message = "O número inserido é muito alto!")
    private Integer numeroLogradouro;

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
        this.cpf = clienteDTO.getCpf();
        this.cadastro = new Cadastro(clienteDTO);
    }
    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }
}
