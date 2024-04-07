package com.lemnos.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cadastro")
@Data
public class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Senha")
    private String Senha;
}
