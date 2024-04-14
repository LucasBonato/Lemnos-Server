package com.lemnos.server.Controllers;

import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Services.FuncionarioService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Funcionario> deleteById(@PathVariable Integer id){
        return funcionarioService.deleteById(id);
    }
}
