package com.lemnos.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Estado")
@Data
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "UF")
    private String UF;
}
