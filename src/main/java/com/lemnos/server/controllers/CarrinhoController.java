package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.CarrinhoRequest;
import com.lemnos.server.models.dtos.responses.CarrinhoResponse;
import com.lemnos.server.services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponse> getCarrinho(@RequestParam(value = "email") String email) {
        return carrinhoService.getCarrinho(email);
    }

    @PostMapping
    public ResponseEntity<Void> adicionarProduto(@RequestBody CarrinhoRequest carrinhoRequest) {
        return carrinhoService.adicionarProduto(carrinhoRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> removerProduto(@RequestBody CarrinhoRequest carrinhoRequest) {
        return carrinhoService.removerProduto(carrinhoRequest);
    }

    @DeleteMapping("/tudo")
    public ResponseEntity<Void> removerTodosProdutos(@RequestBody CarrinhoRequest carrinhoRequest) {
        return carrinhoService.removerTodosProdutos(carrinhoRequest);
    }
}
