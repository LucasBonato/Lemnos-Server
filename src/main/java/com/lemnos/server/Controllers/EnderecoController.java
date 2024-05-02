package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired private EnderecoService enderecoService;

    @PostMapping("/cliente")
    public ResponseEntity createEnderecoCliente(@RequestParam(required = true, value = "id") Integer id, @RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.createEnderecoCliente(id, enderecoDTO);
    }

    @PostMapping("/funcionario")
    public ResponseEntity createEnderecoFuncionario(@RequestParam(required = true, value = "id") Integer id, @RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.createEnderecoFuncionario(id, enderecoDTO);
    }

    @PostMapping("/fornecedor")
    public ResponseEntity createEnderecoFornecedor(@RequestParam(required = true, value = "id") Integer id, @RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.createEnderecoFornecedor(id, enderecoDTO);
    }
}