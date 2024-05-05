package com.lemnos.server.controllers;

import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.services.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired private CadastroService cadastroService;

    @PostMapping("/cliente")
    public ResponseEntity<Void> cadastrarCliente(@RequestBody ClienteRequest clienteRequest){
        return cadastroService.cadastrarCliente(clienteRequest);
    }

    @PostMapping("/funcionario")
    public ResponseEntity<Void> cadastrarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest){
        return cadastroService.cadastrarFuncionario(funcionarioRequest);
    }

    @PostMapping("/fornecedor")
    public ResponseEntity<Void> cadastrarFornecedor(@RequestBody FornecedorRequest fornecedorRequest){
        return cadastroService.cadastrarFornecedor(fornecedorRequest);
    }
}
