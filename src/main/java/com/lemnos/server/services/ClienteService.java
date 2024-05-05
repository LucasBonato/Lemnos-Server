package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.exceptions.cadastro.CadastroNotValidException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.Cliente;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.repositories.ClienteRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService extends Util {

    @Autowired private ClienteRepository clienteRepository;

    public ResponseEntity<List<ClienteResponse>> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteResponse> dto = new ArrayList<>();
        for(Cliente cliente : clientes){
            dto.add(new ClienteResponse(
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getCadastro().getEmail(),
                    cliente.getCadastro().getSenha(),
                    getEnderecoRecords(cliente)
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<ClienteResponse> getOneById(Integer id) {
        Cliente cliente = getOneClienteById(id);
        ClienteResponse record = new ClienteResponse(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getCadastro().getEmail(),
                cliente.getCadastro().getSenha(),
                getEnderecoRecords(cliente)
        );
        return ResponseEntity.ok(record);
    }

    public ResponseEntity<Void> createEndereco(Integer id, EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteById(id);

        Endereco endereco = getEndereco(enderecoRequest);

        Optional<ClientePossuiEndereco> optionalCpe = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), id);
        if(optionalCpe.isPresent()){
            throw new EntityAlreadyHasEnderecoException("Cliente");
        }

        ClientePossuiEndereco clientePossuiEndereco = new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento());
        clientePossuiEnderecoRepository.save(clientePossuiEndereco);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> updateCliente(Integer id, ClienteRequest clienteDTO){
        Cliente updatedCliente = insertData(id, clienteDTO);
        clienteRepository.save(updatedCliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteById(Integer id){
        Cliente clienteDeletado = getOneClienteById(id);

        if(clienteDeletado.getSituacao() == Situacao.ATIVO) {
            clienteDeletado.setSituacao(Situacao.INATIVO);
            clienteRepository.save(clienteDeletado);
        }
        return ResponseEntity.ok().build();
    }

    private Cliente insertData(Integer id, ClienteRequest clienteEnviado) {
        Cliente clienteEncontrado = getOneClienteById(id);

        if(StringUtils.isBlank(clienteEnviado.nome()) && (clienteEnviado.cpf() == null || clienteEnviado.cpf().isBlank())){
            throw new UpdateNotValidException("Cliente");
        }
        if(StringUtils.isBlank(clienteEnviado.nome())){
            clienteEnviado = clienteEnviado.setNome(clienteEncontrado.getNome());
        }
        if(clienteEnviado.nome().length() < 2 || clienteEnviado.nome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(clienteEnviado.cpf() == null){
            clienteEnviado = clienteEnviado.setCpf(clienteEncontrado.getCpf().toString());
        }
        Long cpf = convertStringToLong(clienteEnviado.cpf(), CPF);

        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(clienteOptional.isPresent() && !Objects.equals(clienteOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(id);
        updatedCliente.setNome(clienteEnviado.nome());
        updatedCliente.setCpf(cpf);
        updatedCliente.setCadastro(clienteEncontrado.getCadastro());
        updatedCliente.setEnderecos(clienteEncontrado.getEnderecos());

        return updatedCliente;
    }
}
