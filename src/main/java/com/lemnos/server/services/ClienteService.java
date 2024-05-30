package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.exceptions.cadastro.CadastroNotValidException;
import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.endereco.EnderecoNotFoundException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable("allClientes")
    public ResponseEntity<List<ClienteResponse>> getAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteResponse> dto = new ArrayList<>();
        for(Cliente cliente : clientes){
            dto.add(getClienteResponse(cliente));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<ClienteResponse> getOneById(Integer id) {
        Cliente cliente = getOneClienteById(id);
        ClienteResponse record = getClienteResponse(cliente);
        return ResponseEntity.ok(record);
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

    private static ClienteResponse getClienteResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getCadastro().getEmail(),
                cliente.getCadastro().getSenha(),
                getProdutosFavoritos(cliente),
                getEnderecoRecords(cliente)
        );
    }

    private static List<String> getProdutosFavoritos(Cliente cliente) {
        List<Produto> produtos = cliente.getProdutosFavoritos();
        List<String> produtosFavoritos = new ArrayList<>();
        for (Produto produto : produtos) {
            produtosFavoritos.add(produto.getId().toString());
        }
        return produtosFavoritos;
    }

    private Cliente getOneClienteById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(ClienteNotFoundException::new);
    }
    private static List<EnderecoResponse> getEnderecoRecords(Cliente cliente) {
        List<EnderecoResponse> enderecoResponses = new ArrayList<>();
        for(ClientePossuiEndereco cpe : cliente.getEnderecos()){
            enderecoResponses.add(new EnderecoResponse(
                    cpe.getEndereco().getCep(),
                    cpe.getEndereco().getLogradouro(),
                    cpe.getNumeroLogradouro(),
                    cpe.getComplemento(),
                    cpe.getEndereco().getCidade().getCidade(),
                    cpe.getEndereco().getBairro(),
                    cpe.getEndereco().getEstado().getUf()
            ));
        }
        return enderecoResponses;
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
            throw new CadastroNotValidException(Codigo.NOME, "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(clienteEnviado.cpf() == null){
            clienteEnviado = clienteEnviado.setCpf(clienteEncontrado.getCpf().toString());
        }
        Long cpf = convertStringToLong(clienteEnviado.cpf(), Codigo.CPF);

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