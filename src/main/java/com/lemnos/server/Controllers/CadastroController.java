package com.lemnos.server.Controllers;

import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Services.CadastroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired private CadastroService cadastroService;

    @PostMapping("/cliente")
    public ResponseEntity cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        return cadastroService.cadastrarCliente(clienteDTO);
    }

    @PostMapping("/funcionario")
    public ResponseEntity cadastrarFuncionario(@RequestBody @Valid FuncionarioDTO funcionarioDTO) throws ParseException {
        return cadastroService.cadastrarFuncionario(funcionarioDTO);
    }

    @PostMapping("/fornecedor")
    public ResponseEntity cadastrarFornecedor(@RequestBody @Valid FornecedorDTO fornecedorDTO){
        return cadastroService.cadastrarFornecedor(fornecedorDTO);
    }
}
