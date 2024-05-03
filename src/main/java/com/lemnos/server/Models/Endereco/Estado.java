package com.lemnos.server.Models.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estado")
@Data
@NoArgsConstructor
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    @JsonIgnore
    private Integer id;

    @Column(name = "UF")
    private String uf;
}
