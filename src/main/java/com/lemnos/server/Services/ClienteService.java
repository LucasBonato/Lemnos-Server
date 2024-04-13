package com.lemnos.server.Services;

import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Repositories.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

//    public ResponseEntity<Cliente> updateClient(Cliente cliente, Integer id){
//        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
//        if(clienteOptional.isPresent()){
//            Cliente updatedCliente = getOneById(cliente.getId()).getBody();
//            if (updatedCliente != null) {
//                updatedCliente.setNome(cliente.getNome());
//                updatedCliente.setCpf(cliente.getCpf());
//                updatedCliente.setCep(cliente.getCep());
//                updatedCliente.setNumeroLogradouro(cliente.getNumeroLogradouro());
//                return ResponseEntity.ok(updatedCliente);
//            }
//        }
//        throw new RuntimeException("Não foi possível atualizar os dados do cliente");
//    }
    public ResponseEntity<Cliente> deleteById(Integer id){
        try{
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex){
            throw new RuntimeException("Não foi possível deletar o cliente!");
        }
    }
}
