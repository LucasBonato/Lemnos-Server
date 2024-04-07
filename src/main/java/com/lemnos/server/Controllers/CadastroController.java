package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired private ClienteService clienteService;

    @PostMapping
    public ResponseEntity cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        return clienteService.cadastrarCliente(clienteDTO);
    }
}
