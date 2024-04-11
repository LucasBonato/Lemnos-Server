package com.lemnos.server.Controllers;

import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getALlClientes(){
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getOneCliente(@PathVariable Integer id){
        return clienteService.getOne(id);
    }
}
