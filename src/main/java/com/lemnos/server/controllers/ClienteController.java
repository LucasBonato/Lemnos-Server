package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll(){
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getOneById(@PathVariable Integer id){
        return clienteService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Integer id, @RequestBody ClienteRequest clienteRequest){
        return clienteService.updateCliente(id, clienteRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){ return clienteService.deleteById(id);}

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return clienteService.createEndereco(id, enderecoRequest);
    }
}
