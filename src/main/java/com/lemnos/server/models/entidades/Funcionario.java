package com.lemnos.server.models.entidades;

import com.lemnos.server.annotations.CPF;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.enums.Situacao;
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
    @CPF(message = "CPF preenchido incorretamente!")
    private Long cpf;

    @Column(name = "Data_Nascimento")
    private Date dataNascimento;

    @Column(name = "Data_Admissao")
    private Date dataAdmissao;

    @Column(name = "Telefone")
    private Long telefone;

    @OneToMany(mappedBy = "funcionario")
    private List<FuncionarioPossuiEndereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    public Funcionario(FuncionarioRequest funcionarioRequest, Date dataNascimento, Date dataAdmissao){
        this.nome = funcionarioRequest.nome();
        this.cpf = Long.parseLong(funcionarioRequest.cpf());
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.telefone = Long.parseLong(funcionarioRequest.telefone());
        this.cadastro = new Cadastro(funcionarioRequest);
    }
}
