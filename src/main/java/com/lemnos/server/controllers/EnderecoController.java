package com.lemnos.server.controllers;

import com.lemnos.server.configurations.swagger.EnderecoSwagger;
import com.lemnos.server.models.dtos.requests.EnderecoRemoveRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController implements EnderecoSwagger {
    @Autowired private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<ViaCepDTO> fetchAddressFields(@RequestParam(value = "cep") String cep) {
        return enderecoService.getFields(cep);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody EnderecoRequest enderecoRequest) {
        return enderecoService.createEndereco(enderecoRequest);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody EnderecoRequest enderecoRequest) {
        return enderecoService.updateEndereco(enderecoRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestBody EnderecoRemoveRequest enderecoRequest) {
        return enderecoService.removeEndereco(enderecoRequest);
    }

    @PostMapping("/verificar")
    public ResponseEntity<Void> verifyFields(@RequestBody EnderecoRequest enderecoRequest) {
        return enderecoService.verificarCampos(enderecoRequest);
    }
}