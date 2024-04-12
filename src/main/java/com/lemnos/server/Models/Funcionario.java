package com.lemnos.server.Models;

import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Funcionario")
@Data
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF", unique = true)
    private String cpf;

    @Column(name = "Data_Nascimento")
    private Date dataNascimento;

    @Column(name = "Data_Admissao")
    private Date dataAdmissao;

    @Column(name = "Telefone")
    private String telefone;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "Numero_Logradouro")
    private Integer numeroLogradouro;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Funcionario_Possui_Endereco",
            joinColumns = @JoinColumn(name = "Id_Funcionario"),
            inverseJoinColumns = @JoinColumn(name = "Id_Endereco")
    )
    private List<Endereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    public Funcionario(FuncionarioDTO funcionarioDTO, Date dataNascimento, Date dataAdmissao){
        this.nome = funcionarioDTO.getNome();
        this.cpf = funcionarioDTO.getCpf();
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.telefone = funcionarioDTO.getTelefone();
        this.cep = funcionarioDTO.getCep();
        this.numeroLogradouro = funcionarioDTO.getNumeroLogradouro();
    }
}
