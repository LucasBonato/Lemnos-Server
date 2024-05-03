package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Services.EnderecoService;
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
    public ResponseEntity<Void> update(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.update(enderecoDTO);
    }
}
