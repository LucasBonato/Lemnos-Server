package com.lemnos.server.controllers;

import com.lemnos.server.configurations.swagger.ClienteSwagger;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController implements ClienteSwagger {
    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll(){
        return clienteService.getAll();
    }

    @GetMapping("/find")
    public ResponseEntity<ClienteResponse> getOneByEmail(JwtAuthenticationToken token) {
        return clienteService.getOneByEmail(token);
    }

    @PutMapping
    public ResponseEntity<Void> updateById(JwtAuthenticationToken token, @RequestBody ClienteRequest clienteRequest) {
        return clienteService.updateCliente(token, clienteRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(JwtAuthenticationToken token){
        return clienteService.deleteByEmail(token);
    }
}