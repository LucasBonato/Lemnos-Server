package com.lemnos.server.models.dtos.requests;

public record CategoriaRequest(String nome) {

    public CategoriaRequest setNome(String categoria){
        return new CategoriaRequest(categoria);
    }
}
