package com.lemnos.server.Services;

import com.lemnos.server.Models.Cadastro.VerificationUtils;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.DTOs.FuncionarioDTO;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastroService extends VerificationUtils {

    public ResponseEntity cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = verificarRegraDeNegocio(clienteDTO);

        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = verificarRegraDeNegocio(funcionarioDTO);

        funcionarioRepository.save(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity cadastrarFornecedor(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = verificarRegraDeNegocio(fornecedorDTO);
        fornecedorRepository.save(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
