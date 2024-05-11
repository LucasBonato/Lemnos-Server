package com.lemnos.server.models.dtos.requests;

public record SubCategoriaRequest(String nome) {
    public SubCategoriaRequest setSubcategoria(String nome){
        return new SubCategoriaRequest(nome);
    }
}
