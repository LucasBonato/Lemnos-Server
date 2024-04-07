package com.lemnos.server.Services;

import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired private ClienteRepository clienteRepository;

    public ResponseEntity cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO);
        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
