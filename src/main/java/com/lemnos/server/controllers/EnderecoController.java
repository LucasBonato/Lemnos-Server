package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired private EnderecoService enderecoService;

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody EnderecoRequest enderecoRequest){
        return enderecoService.update(enderecoRequest);
    }
}
