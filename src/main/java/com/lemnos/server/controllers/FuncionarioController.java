package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.FuncionarioResponse;
import com.lemnos.server.services.FuncionarioService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @GetMapping("/me")
    public ResponseEntity<FuncionarioResponse> getOne(JwtAuthenticationToken token) {
        return funcionarioService.getOne(token);
    }

    @GetMapping("/find")
    public ResponseEntity<FuncionarioResponse> getOneByEmail(@PathParam(value = "email") String email){
        return funcionarioService.getOneByEmail(email);
    }

    @GetMapping("/by")
    public ResponseEntity<List<String>> getBy(@RequestParam(value = "nome") String nome) {
        return funcionarioService.getBy(nome);
    }

    @PutMapping
    public ResponseEntity<Void> updateFuncionario(@PathParam(value = "email") String email, @RequestBody @Valid FuncionarioRequest funcionarioRequest){
        return funcionarioService.updateFuncionario(email, funcionarioRequest);
    }

    @PutMapping("/situacao")
    public ResponseEntity<Void> ativarOuDesativar(@RequestBody List<String> emails) {
        return funcionarioService.ativarOuDesativar(emails);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathParam(value = "email") String email){
        return funcionarioService.deleteByEmail(email);
    }
}