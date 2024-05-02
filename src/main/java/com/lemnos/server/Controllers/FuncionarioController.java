package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Services.FuncionarioService;
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
    public ResponseEntity<List<Funcionario>> getAll(){
        return funcionarioService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getOneById(@PathVariable Integer id){
        return funcionarioService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFuncionario(@PathVariable Integer id, @RequestBody @Valid FuncionarioDTO funcionarioDTO){
        return funcionarioService.updateFuncionario(id, funcionarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        return funcionarioService.deleteById(id);
    }

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(required = true, value = "id") Integer id, @RequestBody EnderecoDTO enderecoDTO){
        return funcionarioService.createEndereco(id, enderecoDTO);
    }
}