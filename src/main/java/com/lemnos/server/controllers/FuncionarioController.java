package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.FuncionarioResponse;
import com.lemnos.server.models.dtos.responses.IdResponse;
import com.lemnos.server.services.FuncionarioService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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

    @GetMapping("/find")
    public ResponseEntity<IdResponse> getOneById(@PathParam(value = "email") String email){
        return funcionarioService.getByEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFuncionario(@PathVariable Integer id, @RequestBody @Valid FuncionarioRequest funcionarioRequest){
        return funcionarioService.updateFuncionario(id, funcionarioRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        return funcionarioService.deleteById(id);
    }
}