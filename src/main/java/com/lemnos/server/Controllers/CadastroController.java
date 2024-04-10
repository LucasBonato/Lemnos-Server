package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Services.CadastroService;
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

    @Autowired private CadastroService cadastroService;

    @PostMapping("/cliente")
    public ResponseEntity cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        return cadastroService.cadastrarCliente(clienteDTO);
    }

    @PostMapping("/funcionario")
    public ResponseEntity cadastrarFuncionario(@RequestBody @Valid FuncionarioDTO funcionarioDTO){
        return cadastroService.cadastrarFuncionario(funcionarioDTO);
    }
}
