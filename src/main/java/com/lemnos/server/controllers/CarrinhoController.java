package com.lemnos.server.controllers;

import com.lemnos.server.configurations.swagger.CarrinhoSwagger;
import com.lemnos.server.models.dtos.requests.CarrinhoRequest;
import com.lemnos.server.models.dtos.responses.CarrinhoResponse;
import com.lemnos.server.services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController implements CarrinhoSwagger {
    @Autowired private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponse> getCarrinho(JwtAuthenticationToken token) {
        return carrinhoService.getCarrinho(token);
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Integer> quantidadeProdutos(JwtAuthenticationToken token){
        return carrinhoService.quantidadeProdutos(token);
    }

    @PostMapping
    public ResponseEntity<Void> adicionarProduto(JwtAuthenticationToken token, @RequestBody CarrinhoRequest carrinhoRequest) {
        return carrinhoService.adicionarProduto(token, carrinhoRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> removerProduto(JwtAuthenticationToken token, @RequestBody CarrinhoRequest carrinhoRequest) {
        return carrinhoService.removerProduto(token, carrinhoRequest);
    }

    @DeleteMapping("/tudo")
    public ResponseEntity<Void> removerTodosProdutos(JwtAuthenticationToken token) {
        return carrinhoService.removerTodosProdutos(token);
    }
}
