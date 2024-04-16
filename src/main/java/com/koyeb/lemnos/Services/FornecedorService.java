package com.koyeb.lemnos.Services;

import com.koyeb.lemnos.Models.Fornecedor;
import com.koyeb.lemnos.Repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired private FornecedorRepository fornecedorRepository;

    public ResponseEntity<List<Fornecedor>> getAll() {
        return ResponseEntity.ok(fornecedorRepository.findAll());
    }

    public ResponseEntity<Fornecedor> getOneById(Integer id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if(fornecedorOptional.isPresent()){
            return ResponseEntity.ok(fornecedorOptional.get());
        }
        throw new RuntimeException("O Fornecedor não foi encontrado!");
    }
}
