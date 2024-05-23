package com.lemnos.server.models.produto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.produto.categoria.SubCategoria;
import com.lemnos.server.models.produto.imagens.ImagemPrincipal;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Descricao")
    private String descricao;

    @Column(name = "Cor")
    private String cor;

    @Column(name = "Valor")
    private Double valor;

    @Column(name = "Modelo")
    private String modelo;

    @Column(name = "Peso")
    private Double peso;

    @Column(name = "Altura")
    private Double altura;

    @Column(name = "Comprimento")
    private Double comprimento;

    @Column(name = "Largura")
    private Double largura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Fabricante")
    private Fabricante fabricante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Sub_Categoria")
    private SubCategoria subCategoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Imagem")
    private ImagemPrincipal imagemPrincipal;

    @ManyToMany(mappedBy = "produtosFavoritos")
    @JsonIgnore
    private List<Cliente> clientes;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_Desconto")
    private Desconto desconto;

    public Produto(ProdutoRequest produtoRequest, Fabricante fabricante, SubCategoria subCategoria, ImagemPrincipal imagemPrincipal, Desconto desconto){
        this.nome = produtoRequest.nome();
        this.descricao = produtoRequest.descricao();
        this.cor = produtoRequest.cor();
        this.valor = produtoRequest.valor();
        this.modelo = produtoRequest.modelo();
        this.peso = produtoRequest.peso();
        this.altura = produtoRequest.altura();
        this.comprimento = produtoRequest.comprimento();
        this.largura = produtoRequest.largura();
        this.fabricante = fabricante;
        this.subCategoria = subCategoria;
        this.imagemPrincipal = imagemPrincipal;
        this.desconto = desconto;
    }

    public void setAll(ProdutoRequest produtoRequest, Fabricante fabricante, SubCategoria subCategoria, ImagemPrincipal imagemPrincipal) {
        setNome((StringUtils.isNotBlank(produtoRequest.nome())) ? produtoRequest.nome() : this.nome);
        setDescricao((StringUtils.isNotBlank(produtoRequest.descricao())) ? produtoRequest.descricao() : this.descricao);
        setCor((StringUtils.isNotBlank(produtoRequest.cor())) ? produtoRequest.cor() : this.cor);
        setModelo((StringUtils.isNotBlank(produtoRequest.modelo())) ? produtoRequest.modelo() : this.modelo);
        setValor((produtoRequest.valor() != null) ? produtoRequest.valor() : this.valor);
        setPeso((produtoRequest.peso() != null) ? produtoRequest.peso() : this.peso);
        setAltura((produtoRequest.altura() != null) ? produtoRequest.altura() : this.altura);
        setComprimento((produtoRequest.comprimento() != null) ? produtoRequest.comprimento() : this.comprimento);
        setLargura((produtoRequest.largura() != null) ? produtoRequest.largura() : this.largura);
        setFabricante(fabricante);
        setSubCategoria(subCategoria);
        setImagemPrincipal(imagemPrincipal);
        setDesconto(desconto);
    }
}
