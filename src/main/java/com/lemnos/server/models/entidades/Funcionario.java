package com.lemnos.server.models.entidades;

import com.google.firebase.auth.FirebaseToken;
import com.lemnos.server.annotations.CPF;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.enums.Roles;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.utils.Util;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Funcionario")
@Data
@NoArgsConstructor
public class Funcionario implements UserDetails {
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

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER)
    private List<FuncionarioPossuiEndereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role = Roles.FUNCIONARIO;

    public Funcionario(FuncionarioRequest funcionarioRequest, String password,Date dataNascimento, Date dataAdmissao){
        this.nome = funcionarioRequest.nome();
        this.cpf = Long.parseLong(funcionarioRequest.cpf());
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.telefone = Long.parseLong(funcionarioRequest.telefone());
        this.cadastro = new Cadastro(funcionarioRequest, password);
    }

    public Funcionario(FirebaseToken decodedToken, String senha) {
        this.nome = decodedToken.getName();
        switch (decodedToken.getEmail()) {
            case "lucas.perez.bonato@gmail.com":
                this.cpf = 11122233301L;
                this.dataNascimento = Util.convertData("29/08/2006");
                this.dataAdmissao = Date.from(Instant.now());
                this.telefone = 11972540380L;
                break;
            case "lucasatdriano@gmail.com":
                this.cpf = 11122233302L;
                this.dataNascimento = Util.convertData("01/01/2006");
                this.dataAdmissao = Date.from(Instant.now());
                this.telefone = 11962891098L;
                break;
            case "leandrofamiliafox@gmail.com":
                this.cpf = 11122233303L;
                this.dataNascimento = Util.convertData("30/06/2006");
                this.dataAdmissao = Date.from(Instant.now());
                this.telefone = 11934485241L;
                break;
        }
        this.role = Roles.ADMIN;
        this.cadastro = new Cadastro(decodedToken, senha);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((this.role == Roles.FUNCIONARIO) ? "FUNCIONARIO" : "ADMIN"));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.situacao == Situacao.ATIVO;
    }
}
