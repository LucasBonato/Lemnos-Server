package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Services.CadastroService;
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
    public ResponseEntity<Void> cadastrarCliente(@RequestBody ClienteDTO clienteDTO){
        return cadastroService.cadastrarCliente(clienteDTO);
    }

    @PostMapping("/funcionario")
    public ResponseEntity<Void> cadastrarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO){
        return cadastroService.cadastrarFuncionario(funcionarioDTO);
    }

    @PostMapping("/fornecedor")
    public ResponseEntity<Void> cadastrarFornecedor(@RequestBody FornecedorDTO fornecedorDTO){
        return cadastroService.cadastrarFornecedor(fornecedorDTO);
    }
}
