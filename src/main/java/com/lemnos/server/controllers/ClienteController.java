package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll(JwtAuthenticationToken token){
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getOneById(@PathVariable Integer id){
        return clienteService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Integer id, @RequestBody RegisterRequest registerRequest){
        return clienteService.updateCliente(id, registerRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        return clienteService.deleteById(id);
    }

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return clienteService.createEndereco(id, enderecoRequest);
    }

    @PutMapping("/endereco")
    public ResponseEntity<Void> updateEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return clienteService.updateEndereco(id, enderecoRequest);
    }

    @DeleteMapping("/endereco")
    public ResponseEntity<Void> removeEndereco(@RequestParam(value = "id") Integer id, @RequestParam(value = "cep") String cep){
        return clienteService.removeEndereco(id, cep);
    }
}