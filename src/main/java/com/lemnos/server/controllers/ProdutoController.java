package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/desconto/{id}")
    public ResponseEntity<Void> desconto(@PathVariable UUID id, Produto produto, String desconto) { return produtoService.calcularPorcentagem(produto, desconto); }

    @DeleteMapping("/desconto/{id}")
    public ResponseEntity<Void> retirarDesconto(@PathVariable UUID id, ProdutoRequest produtoRequest, String desconto) { return produtoService.retirarPorcentagem(produtoRequest, desconto);}

    @PutMapping("/desconto/{id}")
    public ResponseEntity<Void> alterarDesconto(@PathVariable UUID id, ProdutoRequest produtoRequest, String desconto){ return  produtoService.alterarPorcentagem(produtoRequest, desconto); }
}
