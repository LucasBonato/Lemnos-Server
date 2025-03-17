package com.lemnos.server.services;

import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityService {
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;
    private final FornecedorService fornecedorService;
    
    public Cliente getOneClienteByEmail(String email) {
        return clienteService.getOneClienteByEmail(email);
    }
    public Funcionario getOneFuncionarioByEmail(String email) {
        return funcionarioService.getOneFuncionarioByEmail(email);
    }
    public Fornecedor getOneFornecedorByEmail(String email) {
        return fornecedorService.getOneFornecedorByEmail(email);
    }
}
