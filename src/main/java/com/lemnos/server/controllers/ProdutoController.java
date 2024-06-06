package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.AvaliacaoRequest;
import com.lemnos.server.models.dtos.requests.ProdutoFiltroRequest;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.FavoritoResponse;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.models.produto.Avaliacao;
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

    @PostMapping("/find")
    public ResponseEntity<List<ProdutoResponse>> getBy(@RequestBody ProdutoFiltroRequest filtroRequest) {
        return produtoService.getBy(filtroRequest);
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

    @GetMapping("/fav")
    public ResponseEntity<List<FavoritoResponse>> getFavoritos(@RequestBody String email) {
        return produtoService.getFavoritos(email);
    }

    @PostMapping("/fav")
    public ResponseEntity<Void> favoritar(@RequestParam(name = "email") String email, @RequestParam(name = "id_prod") String idProd) {
        return produtoService.favoritar(email, idProd);
    }

    @DeleteMapping("/fav")
    public ResponseEntity<Void> desfavoritar(@RequestParam(name = "email") String email, @RequestParam(name = "id_prod") String idProd) {
        return produtoService.desfavoritar(email, idProd);
    }

    @DeleteMapping("/desconto/{id}")
    public ResponseEntity<Void> retirarDesconto(@PathVariable String id) { return produtoService.retirarPorcentagem(id);}

    @PostMapping("/avaliar/{id}")
    public ResponseEntity<Void> avaliar(@PathVariable String id, @RequestBody AvaliacaoRequest avaliacaoRequest) {
        return produtoService.avaliar(id, avaliacaoRequest.avaliacao());
    }
}
