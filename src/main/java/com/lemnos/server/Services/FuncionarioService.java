package com.lemnos.server.Services;

import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired private FuncionarioRepository funcionarioRepository;

    public ResponseEntity<List<Funcionario>> getAll() {
        return ResponseEntity.ok(funcionarioRepository.findAll());
    }

    public ResponseEntity<Funcionario> getOneById(Integer id) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if(funcionarioOptional.isPresent()){
            return ResponseEntity.ok(funcionarioOptional.get());
        }
        throw new RuntimeException("Funcionário não encontrado!");
    }

    public ResponseEntity<Funcionario> deleteById(Integer id){
        try{
            funcionarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex){
            throw new RuntimeException("Não foi possível deletar o funcionário");
        }
    }
}
