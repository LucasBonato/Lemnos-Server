package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Void> createEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return enderecoService.createEndereco(id, enderecoRequest);
    }

    @PutMapping
    public ResponseEntity<Void> updateEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return enderecoService.updateEndereco(id, enderecoRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeEndereco(@RequestParam(value = "id") Integer id, @RequestParam(value = "cep") String cep, @RequestParam(value = "e") String entidade){
        return enderecoService.removeEndereco(id, cep, entidade);
    }
}