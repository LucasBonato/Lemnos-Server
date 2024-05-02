package com.lemnos.server.Models;

import com.lemnos.server.Annotations.CPFValidation;
import com.lemnos.server.Models.Cadastro.Cadastro;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Enums.Situacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Funcionario")
@Data
@NoArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF")
    @CPFValidation(message = "CPF preenchido incorretamente!")
    private String cpf;

    @Column(name = "Data_Nascimento")
    private Date dataNascimento;

    @Column(name = "Data_Admissao")
    private Date dataAdmissao;

    @Column(name = "Telefone")
    private String telefone;

    @Column(name = "Numero_Logradouro")
    private Integer numeroLogradouro;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Funcionario_Possui_Endereco",
            joinColumns = @JoinColumn(name = "Id_Funcionario"),
            inverseJoinColumns = @JoinColumn(name = "CEP")
    )
    private List<Endereco> enderecos;

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    public Funcionario(FuncionarioDTO funcionarioDTO, Date dataNascimento, Date dataAdmissao){
        this.nome = funcionarioDTO.getNome();
        this.cpf = funcionarioDTO.getCpf();
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.telefone = funcionarioDTO.getTelefone();
        this.numeroLogradouro = funcionarioDTO.getNumeroLogradouro();
        this.cadastro = new Cadastro(funcionarioDTO);
    }
}
