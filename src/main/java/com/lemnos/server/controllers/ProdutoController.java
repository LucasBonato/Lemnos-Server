package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAll(){
        return produtoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getOneById(@PathVariable String id){
        return produtoService.getOneById(id);
    }

//    @GetMapping
//    public ResponseEntity<ProdutoResponse> getBy(@RequestParam(name = "c") String categoria, @RequestParam(name = "sc") String subCategoria, @RequestParam(name = "m") String marca, @RequestParam(name = "min_p") String menorPreco, @RequestParam(name = "max_p") String maiorPreco) {
//        return produtoService.getBy(categoria, subCategoria, marca, menorPreco, maiorPreco);
//    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody ProdutoRequest produtoRequest){
        return produtoService.cadastrar(produtoRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ProdutoRequest produtoRequest) {
        return produtoService.update(id, produtoRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return produtoService.delete(id);
    }

    @PostMapping("/fav")
    public ResponseEntity<Void> favoritar(@RequestParam(name = "id_cliente") Integer idCliente, @RequestParam(name = "id_prod") String idProd) {
        return produtoService.favoritar(idCliente, idProd);
    }

    @DeleteMapping("/desfav")
    public ResponseEntity<Void> desfavoritar(@RequestParam(name = "id_cliente") Integer idCliente, @RequestParam(name = "id_prod") String idProd) {
        return produtoService.desfavoritar(idCliente, idProd);
    }
}
