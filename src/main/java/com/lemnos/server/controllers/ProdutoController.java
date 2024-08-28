package com.lemnos.server.controllers;

import com.lemnos.server.configurations.swagger.ProdutoSwagger;
import com.lemnos.server.models.dtos.requests.AvaliacaoRequest;
import com.lemnos.server.models.dtos.requests.ProdutoFiltroRequest;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController implements ProdutoSwagger {

    @Autowired private ProdutoService produtoService;

    @PostMapping("/find")
    public ResponseEntity<List<ProdutoResponse>> getBy(@RequestBody ProdutoFiltroRequest filtroRequest) {
        return produtoService.getBy(filtroRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getOneById(@PathVariable String id){
        return produtoService.getOneById(id);
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody ProdutoRequest produtoRequest){
        return produtoService.register(produtoRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ProdutoRequest produtoRequest) {
        return produtoService.update(id, produtoRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return produtoService.delete(id);
    }

    @GetMapping("/desconto")
    public ResponseEntity<List<ProdutoResponse>> getAllWithDiscount() {
        return produtoService.getAllWithDiscount();
    }

    @DeleteMapping("/desconto/{id}")
    public ResponseEntity<Void> retirarDesconto(@PathVariable String id) {
        return produtoService.retirarPorcentagem(id);
    }

    @PostMapping("/avaliar/{id}")
    public ResponseEntity<Void> avaliar(@PathVariable String id, @RequestBody AvaliacaoRequest avaliacaoRequest) {
        return produtoService.avaliar(id, avaliacaoRequest.avaliacao());
    }
}
