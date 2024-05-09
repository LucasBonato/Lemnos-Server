package com.lemnos.server.models.dtos.requests;

public record ProdutoRequest(String descricao, String cor, Double valor) {

    public ProdutoRequest setDescricao(String descricao) {
        return new ProdutoRequest(descricao, cor(), valor());
    }

    public ProdutoRequest setCor(String cor) {
        return new ProdutoRequest(descricao(), cor, valor());
    }

    public ProdutoRequest setValor(Double valor){
        return new ProdutoRequest(descricao(), cor(), valor);
    }
}
