package com.lemnos.server.models.entidades;

import com.google.firebase.auth.FirebaseToken;
import com.lemnos.server.annotations.CPF;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.endereco.possui.ClientePossuiEndereco;
import com.lemnos.server.models.enums.Roles;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "CPF")
    @CPF(message = "CPF preenchido incorretamente!")
    private Long cpf;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<ClientePossuiEndereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ATIVO;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Produtos_Favoritos",
            joinColumns = @JoinColumn(name = "Id_Cliente"),
            inverseJoinColumns = @JoinColumn(name = "Id_Produto")
    )
    private List<Produto> produtosFavoritos;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role = Roles.CLIENTE;

    public Cliente(RegisterRequest registerRequest){
        this.nome = registerRequest.getNome();
        this.cpf = Long.parseLong(registerRequest.getCpf());
        this.cadastro = new Cadastro(registerRequest);
    }

    public Cliente(FirebaseToken decodedToken, String senha) {
        this.nome = decodedToken.getName();
        this.cadastro = new Cadastro(decodedToken, senha);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Roles.CLIENTE.getRoleWithPrefix()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
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
