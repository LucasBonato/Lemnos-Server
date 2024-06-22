package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.responses.FornecedorResponse;
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

    @GetMapping("/find")
    public ResponseEntity<FornecedorResponse> getOneByEmail(@PathParam(value = "email") String email){
        return fornecedorService.getOneByEmail(email);
    }

    @GetMapping("/by")
    public ResponseEntity<List<FornecedorResponse>> getBy(@RequestParam(value = "nome") String nome) {
        return fornecedorService.getBy(nome);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathParam(value = "email") String email, @RequestBody FornecedorRequest fornecedorRequest) {
        return fornecedorService.updateFornecedor(email, fornecedorRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathParam(value = "email") String email){
        return fornecedorService.deleteByEmail(email);
    }
}