package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.services.ClienteService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll(JwtAuthenticationToken token){
    //public ResponseEntity<List<ClienteResponse>> getAll(){
        return clienteService.getAll();
    }

    @GetMapping("/find")
    public ResponseEntity<ClienteResponse> getOneByEmail(@PathParam(value = "email") String email){
        return clienteService.getOneByEmail(email);
    }

    @PutMapping
    public ResponseEntity<Void> updateById(@PathParam(value = "email") String email, @RequestBody ClienteRequest clienteRequest){
        return clienteService.updateCliente(email, clienteRequest);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathParam(value = "email") String email){
        return clienteService.deleteByEmail(email);
    }
}