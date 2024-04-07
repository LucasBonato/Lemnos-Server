package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroEmailAlreadyInUseException;
import com.lemnos.server.Models.Cadastro;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Repositories.CadastroRepository;
import com.lemnos.server.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CadastroService {
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private CadastroRepository cadastroRepository;

    public ResponseEntity cadastrarCliente(ClienteDTO clienteDTO) {
        clienteDTO.setEmail(clienteDTO.getEmail().toLowerCase());
        verificarCamposCliente(clienteDTO);
        Cliente cliente = new Cliente(clienteDTO);
        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void verificarCamposCliente(ClienteDTO clienteDTO) {
        Optional<Cadastro> cadastroOptional = cadastroRepository.findByEmail(clienteDTO.getEmail());
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(clienteDTO.getCpf());
        if(cadastroOptional.isPresent()) throw new CadastroEmailAlreadyInUseException();
        if(clienteOptional.isPresent()) throw new CadastroCpfAlreadyInUseException();
    }
}
