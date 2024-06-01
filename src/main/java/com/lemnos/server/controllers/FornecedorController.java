package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.responses.FornecedorResponse;
import com.lemnos.server.models.dtos.responses.IdResponse;
import com.lemnos.server.services.FornecedorService;
import jakarta.websocket.server.PathParam;
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

    @GetMapping("/find")
    public ResponseEntity<IdResponse> getOneById(@PathParam(value = "email") String email){
        return fornecedorService.getByEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody FornecedorRequest fornecedorRequest) {
        return fornecedorService.updateFornecedor(id, fornecedorRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        return fornecedorService.deleteById(id);
    }
}