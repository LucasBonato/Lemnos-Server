package com.lemnos.server.models.endereco;

import com.lemnos.server.annotations.CEP;
import com.lemnos.server.models.viacep.ViaCepDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Endereco")
@Data
@NoArgsConstructor
public class Endereco {
    @Id
    @Column(name = "CEP")
    @CEP(message = "CEP Inválido! (XXXXX-XXX)")
    private String cep;

    @Column(name = "Logradouro")
    private String logradouro;

    @Column(name = "Bairro")
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "id_cidade")
    private Cidade cidade;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    public Endereco(ViaCepDTO viaCepDTO, Cidade cidade, Estado estado) {
        this.cep = viaCepDTO.cep();
        this.logradouro = viaCepDTO.logradouro();
        this.bairro = viaCepDTO.bairro();
        this.cidade = cidade;
        this.estado = estado;
    }
}
