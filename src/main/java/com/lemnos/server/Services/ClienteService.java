package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroNotValidException;
import com.lemnos.server.Exceptions.Cliente.ClienteNotFoundException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.ClienteDTO;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Enums.Situacao;
import com.lemnos.server.Models.Records.ClienteRecord;
import com.lemnos.server.Repositories.ClienteRepository;
import com.lemnos.server.Utils.Util;
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

    public ResponseEntity<List<ClienteRecord>> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteRecord> dto = new ArrayList<>();
        for(Cliente cliente : clientes){
            dto.add(new ClienteRecord(
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getCadastro().getEmail(),
                    cliente.getCadastro().getSenha(),
                    getEnderecoRecords(cliente)
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<Cliente> getOneById(Integer id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.get());
        }
        throw new ClienteNotFoundException();
    }

    public ResponseEntity<Void> createEndereco(Integer id, EnderecoDTO enderecoDTO) {
        Cliente cliente = getOneById(id).getBody();
        assert cliente != null;

        Endereco endereco = getEndereco(enderecoDTO);

        ClientePossuiEndereco clientePossuiEndereco = new ClientePossuiEndereco(cliente, endereco, enderecoDTO.getNumeroLogradouro());
        clientePossuiEnderecoRepository.save(clientePossuiEndereco);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
        Long cpf = convertStringToLong(clienteEnviado.getCpf(), CPF);

        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if(clienteOptional.isPresent() && !Objects.equals(clienteOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(id);
        updatedCliente.setNome(clienteEnviado.getNome());
        updatedCliente.setCpf(cpf);
        updatedCliente.setCadastro(clienteEncontrado.getCadastro());
        updatedCliente.setEnderecos(clienteEncontrado.getEnderecos());

        return updatedCliente;
    }
}
