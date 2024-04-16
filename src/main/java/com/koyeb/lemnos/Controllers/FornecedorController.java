package com.koyeb.lemnos.Controllers;

import com.koyeb.lemnos.Models.Fornecedor;
import com.koyeb.lemnos.Services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
