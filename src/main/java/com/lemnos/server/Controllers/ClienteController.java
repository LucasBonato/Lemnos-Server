package com.lemnos.server.Controllers;

import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll(){
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getOneById(@PathVariable Integer id){
        return clienteService.getOneById(id);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Cliente> updateById(@PathVariable Integer id){ return clienteService.updateClient(Cliente, id); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deleteById(@PathVariable Integer id){ return clienteService.deleteById(id);}
}
