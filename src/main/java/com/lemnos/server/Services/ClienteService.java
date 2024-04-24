package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroNotValidException;
import com.lemnos.server.Exceptions.Cliente.ClienteNotFoundException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Repositories.ClienteRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        throw new ClienteNotFoundException();
    }

    public ResponseEntity<Void> updateCliente(Integer id, ClienteDTO clienteDTO){
        Cliente updatedCliente = insertData(id, clienteDTO);
        clienteRepository.save(updatedCliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteById(Integer id){
        getOneById(id);
        clienteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private Cliente insertData(Integer id, ClienteDTO clienteEnviado) {
        Cliente clienteEncontrado = getOneById(id).getBody();
        assert clienteEncontrado != null;

        if(StringUtils.isBlank(clienteEnviado.getNome()) && StringUtils.isBlank(clienteEnviado.getCpf())){
            throw new UpdateNotValidException("Cliente");
        }
        if(StringUtils.isBlank(clienteEnviado.getNome())){
            clienteEnviado.setNome(clienteEncontrado.getNome());
        }
        if(clienteEnviado.getNome().length() < 2 || clienteEnviado.getNome().length() > 40){
            throw new CadastroNotValidException("O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(StringUtils.isBlank(clienteEnviado.getCpf())){
            clienteEnviado.setCpf(clienteEncontrado.getCpf());
        }

        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(clienteEnviado.getCpf());
        if(clienteOptional.isPresent() && !Objects.equals(clienteOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(id);
        updatedCliente.setNome(clienteEnviado.getNome());
        updatedCliente.setCpf(clienteEnviado.getCpf());
        updatedCliente.setCadastro(clienteEncontrado.getCadastro());
        updatedCliente.setEnderecos(clienteEncontrado.getEnderecos());

        return updatedCliente;
    }
}
