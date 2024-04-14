package com.lemnos.server.Services;

import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Repositories.ClienteRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired private ClienteRepository clienteRepository;

    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    public ResponseEntity<Cliente> getOneById(Integer id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.get());
        }
        throw new RuntimeException("Cliente não encontrado");
    }

    public ResponseEntity<Cliente> updateCliente(Integer id, ClienteDTO clienteDTO){
        Cliente updatedCliente = insertData(id, clienteDTO);
        clienteRepository.save(updatedCliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Cliente> deleteById(Integer id){
        getOneById(id);
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Cliente insertData(Integer id, ClienteDTO clienteEnviado) {
        Cliente clienteEncontrado = getOneById(id).getBody();
        assert clienteEncontrado != null;

        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(id);

        if(StringUtils.isBlank(clienteEnviado.getNome()) && StringUtils.isNotBlank(clienteEncontrado.getNome())){
            updatedCliente.setNome(clienteEncontrado.getNome());
        } else {
            updatedCliente.setNome(clienteEnviado.getNome());
        }
        if(StringUtils.isBlank(clienteEnviado.getCpf()) && StringUtils.isNotBlank(clienteEncontrado.getCpf())){
            updatedCliente.setCpf(clienteEncontrado.getCpf());
        } else {
            updatedCliente.setCpf(clienteEnviado.getCpf());
        }
        if(clienteEnviado.getNumeroLogradouro() == null){
            updatedCliente.setNumeroLogradouro(clienteEncontrado.getNumeroLogradouro());
        } else {
            updatedCliente.setNumeroLogradouro(clienteEnviado.getNumeroLogradouro());
        }
        updatedCliente.setCadastro(clienteEncontrado.getCadastro());
        updatedCliente.setEnderecos(clienteEncontrado.getEnderecos());

        return updatedCliente;
    }
}
