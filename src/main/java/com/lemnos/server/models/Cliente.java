package com.lemnos.server.models;

import com.lemnos.server.annotations.CPF;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.models.enums.Situacao;
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

    public Cliente(ClienteRequest clienteRequest){
        this.nome = clienteRequest.nome();
        this.cpf = Long.parseLong(clienteRequest.cpf());
        this.cadastro = new Cadastro(clienteRequest);
    }
}
