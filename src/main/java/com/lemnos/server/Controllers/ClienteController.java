package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.Records.ClienteRecord;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
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
    public ResponseEntity<List<ClienteRecord>> getAll(){
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRecord> getOneById(@PathVariable Integer id){
        return clienteService.getOneById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO){
        return clienteService.updateCliente(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){ return clienteService.deleteById(id);}

    @PostMapping("/endereco")
    public ResponseEntity<Void> createEndereco(@RequestParam(required = true, value = "id") Integer id, @RequestBody EnderecoDTO enderecoDTO){
        return clienteService.createEndereco(id, enderecoDTO);
    }
}
