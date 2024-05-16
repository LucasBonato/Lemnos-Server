package com.lemnos.server.models.cadastro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.dtos.requests.auth.LoginRequest;
import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "Cadastro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @JsonIgnore
    private Integer id;

    @Column(name = "Email")
    @Email(message = "Insira um Email válido")
    private String email;

    @Column(name = "Senha")
    private String senha;

    public Cadastro(RegisterRequest registerRequest){
        this.email = registerRequest.getEmail();
        this.senha = registerRequest.getSenha();
    }

    public Cadastro(FuncionarioRequest funcionarioRequest){
        this.email = funcionarioRequest.email();
        this.senha = funcionarioRequest.senha();
    }

    public Cadastro(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.senha(), this.senha);
    }
}
