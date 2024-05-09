package com.lemnos.server.controllers;

import com.lemnos.server.models.Produto;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAll(){ return produtoService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getOneById(Integer id){ return produtoService.getOneById(id); }
}
