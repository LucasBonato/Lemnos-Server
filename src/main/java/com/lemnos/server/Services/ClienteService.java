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
        Cliente clienteEncontrado = getOneById(id).getBody();

        Cliente updatedCliente = insertData(clienteEncontrado, clienteDTO);
        clienteRepository.save(updatedCliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Cliente> deleteById(Integer id){
        try{
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex){
            throw new RuntimeException("Não foi possível deletar o cliente!");
        }
    }

    private Cliente insertData(Cliente clienteEncontrado, ClienteDTO clienteEnviado) {
        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(clienteEncontrado.getId());

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
