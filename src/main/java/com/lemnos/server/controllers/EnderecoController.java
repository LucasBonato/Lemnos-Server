package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<ViaCepDTO> getFieldsEndereco(@RequestParam(value = "cep") String cep){
        return enderecoService.getFields(cep);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return enderecoService.createEndereco(id, enderecoRequest);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return enderecoService.updateEndereco(id, enderecoRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam(value = "id") Integer id, @RequestParam(value = "cep") String cep, @RequestParam(value = "e") String entidade){
        return enderecoService.removeEndereco(id, cep, entidade);
    }

    @PostMapping("/verificar")
    public ResponseEntity<Void> verificarCampos(@RequestParam(value = "id") Integer id, @RequestBody EnderecoRequest enderecoRequest){
        return enderecoService.verificarCampos(id, enderecoRequest);
    }
}