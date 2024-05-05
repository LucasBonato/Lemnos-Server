package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.FuncionarioResponse;
import com.lemnos.server.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
class FuncionarioController {

    @Autowired private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> getAll(){
        return funcionarioService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> getOneById(@PathVariable Integer id){
        return funcionarioService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFuncionario(@PathVariable Integer id, @RequestBody @Valid FuncionarioRequest funcionarioRequest){
        return funcionarioService.updateFuncionario(id, funcionarioRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        return funcionarioService.deleteById(id);
    }

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return funcionarioService.createEndereco(id, enderecoRequest);
    }

    @PutMapping("/endereco")
    public ResponseEntity<Void> updateEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return funcionarioService.updateEndereco(id, enderecoRequest);
    }
}