package com.lemnos.server.Models;

import com.lemnos.server.Models.Endereco.Endereco;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Cliente")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome")
    private String Nome;

    @Column(name = "CPF", unique = true)
    private String CPF;

    @Column(name = "CEP")
    private String CEP;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Cliente_Possui_Endereco",
            joinColumns = @JoinColumn(name = "Id_Cliente"),
            inverseJoinColumns = @JoinColumn(name = "Id_Endereco")
    )
    private List<Endereco> enderecos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cadastro")
    private Cadastro cadastro;
}
