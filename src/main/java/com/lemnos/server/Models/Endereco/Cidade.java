package com.lemnos.server.Models.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cidade")
@Data
@NoArgsConstructor
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @JsonIgnore
    private Integer id;

    @Column(name = "Cidade")
    private String cidade;

    public Cidade(String cidade){
        this.cidade = cidade;
    }
}
