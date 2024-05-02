package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAll(){
        return fornecedorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getOneById(@PathVariable Integer id){
        return fornecedorService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody FornecedorDTO fornecedorDTO) {
        return fornecedorService.updateFornecedor(id, fornecedorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        return fornecedorService.deleteById(id);
    }

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(required = true, value = "id") Integer id, @RequestBody EnderecoDTO enderecoDTO){
        return fornecedorService.createEndereco(id, enderecoDTO);
    }
}