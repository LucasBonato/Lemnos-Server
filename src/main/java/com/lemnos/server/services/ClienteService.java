package com.lemnos.server.services;

import com.lemnos.server.exceptions.auth.TokenNotValidOrExpiredException;
import com.lemnos.server.exceptions.cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.exceptions.cadastro.CadastroNotValidException;
import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.dtos.requests.ClienteRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.models.endereco.possui.ClientePossuiEndereco;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.dtos.responses.ClienteResponse;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService extends Util {
    private final ClienteRepository clienteRepository;
    private final CadastroRepository cadastroRepository;

    @Cacheable("allClientes")
    public ResponseEntity<List<ClienteResponse>> getAll() {
        List<ClienteResponse> clientes = clienteRepository.findAll().stream()
                .map(this::getClienteResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientes);
    }

    public ResponseEntity<ClienteResponse> getOneByEmail(JwtAuthenticationToken token) {
        verificarToken(token);
        Cliente cliente = getOneClienteByEmail(token.getName());
        ClienteResponse record = getClienteResponse(cliente);
        return ResponseEntity.ok(record);
    }

    public ResponseEntity<Void> updateCliente(JwtAuthenticationToken token, ClienteRequest clienteDTO) {
        verificarToken(token);
        Cliente updatedCliente = insertData(token.getName(), clienteDTO);
        clienteRepository.save(updatedCliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteByEmail(JwtAuthenticationToken token) {
        verificarToken(token);
        Cliente clienteDeletado = getOneClienteByEmail(token.getName());

        if (clienteDeletado.getSituacao() == Situacao.ATIVO) {
            clienteDeletado.setSituacao(Situacao.INATIVO);
            clienteRepository.save(clienteDeletado);
        }
        return ResponseEntity.ok().build();
    }

    private ClienteResponse getClienteResponse(Cliente cliente) {
        return new ClienteResponse(
            cliente.getNome(),
            cliente.getCadastro().getEmail(),
            cliente.getSituacao().toString(),
            cliente.getCpf() == null ? "" : cliente.getCpf().toString(),
            getProdutosFavoritos(cliente),
            getEnderecoRecords(cliente)
        );
    }

    private static List<String> getProdutosFavoritos(Cliente cliente) {
        return cliente.getProdutosFavoritos().stream()
                .map(produto -> produto.getId().toString())
                .collect(Collectors.toList());
    }

    private void verificarToken(JwtAuthenticationToken token) {
        if (token == null) throw new TokenNotValidOrExpiredException();
    }

    private Cliente getOneClienteByEmail(String email) {
        return clienteRepository.findByCadastro(
                cadastroRepository.findByEmail(email.replace("%40", "@")).orElseThrow(ClienteNotFoundException::new)
        ).orElseThrow(ClienteNotFoundException::new);
    }

    private static List<EnderecoResponse> getEnderecoRecords(Cliente cliente) {
        return cliente.getEnderecos().stream()
                .map(clienteEndereco -> new EnderecoResponse(
                        clienteEndereco.getEndereco().getCep(),
                        clienteEndereco.getEndereco().getLogradouro(),
                        clienteEndereco.getNumeroLogradouro(),
                        clienteEndereco.getComplemento(),
                        clienteEndereco.getEndereco().getCidade().getCidade(),
                        clienteEndereco.getEndereco().getBairro(),
                        clienteEndereco.getEndereco().getEstado().getUf()
                ))
                .collect(Collectors.toList());
    }

    private Cliente insertData(String email, ClienteRequest clienteEnviado) {
        Cliente clienteEncontrado = getOneClienteByEmail(email);

        if (StringUtils.isBlank(clienteEnviado.nome()) && (clienteEnviado.cpf() == null || clienteEnviado.cpf().isBlank())) {
            throw new UpdateNotValidException("Cliente");
        }
        if (StringUtils.isBlank(clienteEnviado.nome())) {
            clienteEnviado = clienteEnviado.setNome(clienteEncontrado.getNome());
        }
        if (clienteEnviado.nome().length() < 2 || clienteEnviado.nome().length() > 40) {
            throw new CadastroNotValidException(Codigo.NOME, "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if (clienteEnviado.cpf() == null) {
            clienteEnviado = clienteEnviado.setCpf(clienteEncontrado.getCpf().toString());
        }
        Long cpf = convertStringToLong(clienteEnviado.cpf(), Codigo.CPF);

        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        if (clienteOptional.isPresent() && !Objects.equals(clienteOptional.get().getId(), clienteEncontrado.getId()))
            throw new CadastroCpfAlreadyInUseException();

        Cliente updatedCliente = new Cliente();
        updatedCliente.setId(clienteEncontrado.getId());
        updatedCliente.setNome(clienteEnviado.nome());
        updatedCliente.setCpf(cpf);
        updatedCliente.setCadastro(clienteEncontrado.getCadastro());
        updatedCliente.setEnderecos(clienteEncontrado.getEnderecos());

        return updatedCliente;
    }
}