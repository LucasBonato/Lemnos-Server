package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Endereco.EnderecoNotValidException;
import com.lemnos.server.Exceptions.Endereco.EstadoNotFoundException;
import com.lemnos.server.Exceptions.Fornecedor.FornecedorAlreadyHasEnderecoException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.Endereco.Cidade;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Endereco.Estado;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Repositories.*;
import com.lemnos.server.Utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService extends Util{

    @Autowired private EnderecoRepository enderecoRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private EstadoRepository estadoRepository;

    @Autowired private ClienteService clienteService;
    @Autowired private ClientePossuiEnderecoRepository clientePossuiEnderecoRepository;

    @Autowired private FuncionarioService funcionarioService;
    @Autowired private FuncionarioPossuiEnderecoRepository funcionarioPossuiEnderecoRepository;

    @Autowired private FornecedorService fornecedorService;
    @Autowired private FornecedorRepository fornecedorRepository;

    public ResponseEntity createEnderecoCliente(Integer id, EnderecoDTO enderecoDTO) {
        Cliente cliente = clienteService.getOneById(id).getBody();
        assert cliente != null;

        Endereco endereco = getEndereco(enderecoDTO);

        ClientePossuiEndereco clientePossuiEndereco = new ClientePossuiEndereco(cliente, endereco, enderecoDTO.getNumeroLogradouro());
        clientePossuiEnderecoRepository.save(clientePossuiEndereco);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity createEnderecoFuncionario(Integer id, EnderecoDTO enderecoDTO) {
        Funcionario funcionario = funcionarioService.getOneById(id).getBody();
        assert funcionario != null;

        Endereco endereco = getEndereco(enderecoDTO);

        FuncionarioPossuiEndereco funcionarioPossuiEndereco = new FuncionarioPossuiEndereco(funcionario, endereco, enderecoDTO.getNumeroLogradouro());
        funcionarioPossuiEnderecoRepository.save(funcionarioPossuiEndereco);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity createEnderecoFornecedor(Integer id, EnderecoDTO enderecoDTO) {
        Fornecedor fornecedor = fornecedorService.getOneById(id).getBody();
        assert fornecedor != null;

        Endereco endereco = getEndereco(enderecoDTO);

        if(fornecedor.getEndereco() != null) throw new FornecedorAlreadyHasEnderecoException();

        fornecedor.setEndereco(endereco);
        fornecedorRepository.save(fornecedor);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Endereco getEndereco(EnderecoDTO enderecoDTO) {
        String cep = enderecoDTO.getCep();
        Endereco endereco;

        Optional<Endereco> optionalEndereco = enderecoRepository.findById(cep);
        if(optionalEndereco.isEmpty()){
            verificarCamposEndereco(enderecoDTO);
            endereco = cadastrarNovoEndereco(cep, enderecoDTO);
        } else {
            endereco = optionalEndereco.get();
        }
        return endereco;
    }

    private void verificarCamposEndereco(EnderecoDTO enderecoDTO) {
        if(enderecoDTO.getLogradouro().isBlank() || enderecoDTO.getBairro().isBlank() || enderecoDTO.getCidade().isBlank() || enderecoDTO.getUf().isBlank() || enderecoDTO.getNumeroLogradouro() == null){
            throw new EnderecoNotValidException(Codigo.GLOBAL.ordinal(), "Todos os campos de endereço são obrigatórios!");
        }
        if(enderecoDTO.getLogradouro().length() < 2 || enderecoDTO.getLogradouro().length() > 50){
            throw new EnderecoNotValidException(Codigo.LOGRADOURO.ordinal(), "Logradouro precisa estar entre 2 e 50 caracteres!");
        }
        if(enderecoDTO.getCidade().length() < 2 || enderecoDTO.getCidade().length() > 30){
            throw new EnderecoNotValidException(Codigo.CIDADE.ordinal(), "Cidade precisa estar entre 2 e 30 caracteres!");
        }
        if(enderecoDTO.getBairro().length() < 2 || enderecoDTO.getBairro().length() > 30){
            throw new EnderecoNotValidException(Codigo.BAIRRO.ordinal(), "Bairro precisa estar entre 2 e 30 caracteres!");
        }
        if(enderecoDTO.getUf().length() != 2){
            throw new EnderecoNotValidException(Codigo.UF.ordinal(), "O UF precisa ter 2 caracteres!");
        }
        if(enderecoDTO.getNumeroLogradouro() < 0 || enderecoDTO.getNumeroLogradouro() > 9999){
            throw new EnderecoNotValidException(Codigo.NUMERO_LOGRADOURO.ordinal(), "O número de Logradouro não pode ser negativo ou maior que 9999");
        }
    }

    private Endereco cadastrarNovoEndereco(String cep, EnderecoDTO enderecoDTO) {
        Cidade cidade;

        enderecoDTO.setCidade(enderecoDTO.getCidade().toLowerCase());
        enderecoDTO.setBairro(enderecoDTO.getBairro().toLowerCase());
        enderecoDTO.setUf(enderecoDTO.getUf().toUpperCase());

        Optional<Cidade> cidadeOptional = cidadeRepository.findByCidade(enderecoDTO.getCidade());
        if(cidadeOptional.isEmpty()){
            Cidade novaCidade = new Cidade(enderecoDTO.getCidade());
            cidade = cidadeRepository.save(novaCidade);
        } else {
            cidade = cidadeOptional.get();
        }
        Estado estado = estadoRepository.findByUf(enderecoDTO.getUf()).orElseThrow(EstadoNotFoundException::new);

        Endereco endereco = new Endereco(cep, cidade, estado, enderecoDTO);
        return enderecoRepository.save(endereco);
    }
}