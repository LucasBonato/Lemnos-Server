package com.lemnos.server.services;

import com.lemnos.server.exceptions.endereco.EnderecoNotFoundException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.possui.ClientePossuiEndereco;
import com.lemnos.server.models.endereco.possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.repositories.endereco.possui.ClientePossuiEnderecoRepository;
import com.lemnos.server.repositories.endereco.possui.FuncionarioPossuiEnderecoRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.utils.UtilEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService extends UtilEndereco {
    @Autowired private FornecedorRepository fornecedorRepository;
    @Autowired private ClientePossuiEnderecoRepository clientePossuiEnderecoRepository;
    @Autowired private FuncionarioPossuiEnderecoRepository funcionarioPossuiEnderecoRepository;

    public ResponseEntity<Void> createEndereco(EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);
        switch (enderecoRequest.entidade()){
            case "funcionario":
                createEnderecoFuncionario(enderecoRequest);
                break;
            case "fornecedor":
                createEnderecoFornecedor(enderecoRequest);
                break;
            default:
                createEnderecoCliente(enderecoRequest);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> updateEndereco(EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);
        switch (enderecoRequest.entidade()){
            case "funcionario":
                updateEnderecoFuncionario(enderecoRequest);
                break;
            case "fornecedor":
                updateEnderecoFornecedor(enderecoRequest);
                break;
            default:
                updateEnderecoCliente(enderecoRequest);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removeEndereco(String email, String cep, String entidade) {
        switch (entidade){
            case "funcionario":
                removeEnderecoFuncionario(email, cep);
                break;
            case "fornecedor":
                removeEnderecoFornecedor(email, cep);
                break;
            default:
                removeEnderecoCliente(email, cep);
        }
        return ResponseEntity.ok().build();
    }


    private void createEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteByEmail(enderecoRequest.email());

        Endereco endereco = getEndereco(enderecoRequest);

        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), cliente.getId());
        if(cpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Cliente");

        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void createEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), funcionario.getId());
        if(fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Funcionário");

        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));

    }
    private void createEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);

        if(fornecedor.getEndereco() != null) throw new EntityAlreadyHasEnderecoException("Fornecedor", "já possui um endereço cadastrado!");

        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }

    private void updateEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), cliente.getId());
        if(cpeOptional.isEmpty()) throw new EnderecoNotFoundException("Cliente");

        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> cpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), funcionario.getId());
        if(cpeOptional.isEmpty()) throw new EnderecoNotFoundException("Cliente");

        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);

        if(fornecedor.getEndereco() == null) throw new EnderecoNotFoundException("Fornecedor");

        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }

    private void removeEnderecoCliente(String email, String cep) {
        Cliente cliente = getOneClienteByEmail(email);
        ClientePossuiEndereco cpe = clientePossuiEnderecoRepository.findByCepAndId_Cliente(cep, cliente.getId()).orElseThrow(() -> new EnderecoNotFoundException("Cliente"));
        clientePossuiEnderecoRepository.delete(cpe);
    }
    private void removeEnderecoFuncionario(String email, String cep) {
        Funcionario funcionario = getOneFuncionarioByEmail(email);
        FuncionarioPossuiEndereco fpe = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(cep, funcionario.getId()).orElseThrow(() -> new EnderecoNotFoundException("Funcionário"));
        funcionarioPossuiEnderecoRepository.delete(fpe);
    }
    private void removeEnderecoFornecedor(String email, String cep) {
        Fornecedor fornecedor = getOneFornecedorByEmail(email);

        if(!fornecedor.getEndereco().getCep().equals(cep)) throw new EnderecoNotFoundException("Fornecedor");

        fornecedor.setEndereco(null);
        fornecedorRepository.save(fornecedor);
    }

    public ResponseEntity<Void> verificarCampos(EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);

        ViaCepDTO via = getViaCepObject(enderecoRequest.cep());
        if(via == null) throw new EnderecoNotValidException(Codigo.CEP ,"Cep não existe!");

        String email = enderecoRequest.email();

        switch (enderecoRequest.entidade()){
            case "funcionario":
                Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(enderecoRequest.cep(), getOneFuncionarioByEmail(email).getId());
                if(fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Funcionário");
                break;
            case "fornecedor":
                if(getOneFornecedorByEmail(email).getEndereco() != null) throw new EntityAlreadyHasEnderecoException("Fornecedor", "já possui um endereço cadastrado!");
                break;
            default:
                Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(enderecoRequest.cep(), getOneClienteByEmail(email).getId());
                if(cpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Cliente");
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<ViaCepDTO> getFields(String cep) {
        return ResponseEntity.ok(getViaCepObject(cep));
    }
}