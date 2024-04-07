package com.lemnos.server.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
