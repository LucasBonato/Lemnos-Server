package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.responses.FornecedorResponse;
import com.lemnos.server.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> getAll(){
        return fornecedorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> getOneById(@PathVariable Integer id){
        return fornecedorService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody FornecedorRequest fornecedorRequest) {
        return fornecedorService.updateFornecedor(id, fornecedorRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        return fornecedorService.deleteById(id);
    }

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return fornecedorService.createEndereco(id, enderecoRequest);
    }

    @PutMapping("/endereco")
    public ResponseEntity<Void> updateEndereco(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return fornecedorService.updateEndereco(id, enderecoRequest);
    }
}