package com.lemnos.server.Models.Endereco;

import com.lemnos.server.Models.DTOs.EnderecoDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Estado")
@Data
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "UF")
    private String uf;

    public Estado(EnderecoDTO enderecoDTO){
        this.uf = enderecoDTO.getUf();
    }
}
