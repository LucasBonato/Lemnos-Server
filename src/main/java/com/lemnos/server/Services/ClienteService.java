package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroNotValidException;
import com.lemnos.server.Exceptions.Cliente.ClienteNotFoundException;
import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Enums.Situacao;
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
        Cliente clienteDeletado = getOneById(id).getBody();

        assert clienteDeletado != null;
        if(clienteDeletado.getSituacao() == Situacao.ATIVO) {
            clienteDeletado.setSituacao(Situacao.INATIVO);
            clienteRepository.save(clienteDeletado);
        }
        return ResponseEntity.ok().build();
    }

    private Cliente insertData(Integer id, ClienteDTO clienteEnviado) {
        Long CPF;
        Cliente clienteEncontrado = getOneById(id).getBody();
        assert clienteEncontrado != null;

        if(StringUtils.isBlank(clienteEnviado.getNome()) && (clienteEnviado.getCpf() == null || clienteEnviado.getCpf().isBlank())){
            throw new UpdateNotValidException("Cliente");
        }
        if(StringUtils.isBlank(clienteEnviado.getNome())){
            clienteEnviado.setNome(clienteEncontrado.getNome());
        }
        if(clienteEnviado.getNome().length() < 2 || clienteEnviado.getNome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(clienteEnviado.getCpf() == null){
            clienteEnviado.setCpf(clienteEncontrado.getCpf().toString());
        }
        try{
            CPF = Long.parseLong(clienteEnviado.getCpf());
        } catch (NumberFormatException e) {
            throw new CpfNotValidException("CPF inválido: utilize só números");
        }

        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(CPF);
        if(clienteOptional.isPresent() && !Objects.equals(clienteOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(id);
        updatedCliente.setNome(clienteEnviado.getNome());
        updatedCliente.setCpf(CPF);
        updatedCliente.setCadastro(clienteEncontrado.getCadastro());
        updatedCliente.setEnderecos(clienteEncontrado.getEnderecos());

        return updatedCliente;
    }
}
