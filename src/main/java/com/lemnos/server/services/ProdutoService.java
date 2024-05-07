package com.lemnos.server.services;

import com.lemnos.server.exceptions.entidades.produto.ProdutoNotFoundException;
import com.lemnos.server.models.Produto;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired private ProdutoRepository produtoRepository;

    public ResponseEntity<List<ProdutoResponse>> getAll(){
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponse> dto = new ArrayList<>();

        for(Produto produto: produtos){
            dto.add(new ProdutoResponse(
                    produto.getDescricao(),
                    produto.getCor(),
                    produto.getValor()
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<ProdutoResponse> getOneById(Integer id){
        Produto produto = getProdutoById(id);

        ProdutoResponse response = new ProdutoResponse(
                produto.getDescricao(),
                produto.getCor(),
                produto.getValor()
        );
        return ResponseEntity.ok(response);
    }

    private Produto getProdutoById(Integer id){
        return produtoRepository.findById(id).orElseThrow(ProdutoNotFoundException::new);
    }
}
