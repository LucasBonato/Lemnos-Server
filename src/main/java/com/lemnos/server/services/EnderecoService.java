package com.lemnos.server.services;

import com.lemnos.server.exceptions.endereco.EnderecoNotFoundException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.models.dtos.requests.EnderecoRemoveRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.possui.ClientePossuiEndereco;
import com.lemnos.server.models.endereco.possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Entidade;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.repositories.endereco.possui.ClientePossuiEnderecoRepository;
import com.lemnos.server.repositories.endereco.possui.FuncionarioPossuiEnderecoRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.utils.UtilEndereco;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService extends UtilEndereco {
    private final FornecedorRepository fornecedorRepository;
    private final ClientePossuiEnderecoRepository clientePossuiEnderecoRepository;
    private final FuncionarioPossuiEnderecoRepository funcionarioPossuiEnderecoRepository;
    
    public ResponseEntity<Void> createEndereco(EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);
        switch (enderecoRequest.entidade()) {
            case Entidade.FUNCIONARIO:
                createEnderecoFuncionario(enderecoRequest);
                break;
            case Entidade.FORNECEDOR:
                createEnderecoFornecedor(enderecoRequest);
                break;
            case Entidade.CLIENTE:
                createEnderecoCliente(enderecoRequest);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    public ResponseEntity<Void> updateEndereco(EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);
        switch (enderecoRequest.entidade()) {
            case Entidade.FUNCIONARIO:
                updateEnderecoFuncionario(enderecoRequest);
                break;
            case Entidade.FORNECEDOR:
                updateEnderecoFornecedor(enderecoRequest);
                break;
            case Entidade.CLIENTE:
                updateEnderecoCliente(enderecoRequest);
        }
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity<Void> removeEndereco(EnderecoRemoveRequest enderecoRequest) {
        switch (enderecoRequest.entidade()) {
            case Entidade.FUNCIONARIO:
                removeEnderecoFuncionario(enderecoRequest);
                break;
            case Entidade.FORNECEDOR:
                removeEnderecoFornecedor(enderecoRequest);
                break;
            case Entidade.CLIENTE:
                removeEnderecoCliente(enderecoRequest);
        }
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity<Void> verificarCampos(EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);
        
        ViaCepDTO via = getViaCepObject(enderecoRequest.cep());
        if (via == null) throw new EnderecoNotValidException(Codigo.CEP, "Cep não existe!");
        
        String email = enderecoRequest.email();
        
        switch (enderecoRequest.entidade()) {
            case Entidade.FUNCIONARIO:
                funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(
                    enderecoRequest.cep(),
                    getOneFuncionarioByEmail(email).getId()
                ).orElseThrow(() -> new EntityAlreadyHasEnderecoException("Funcionário"));
                break;
            case Entidade.FORNECEDOR:
                if (getOneFornecedorByEmail(email).getEndereco() != null)
                    throw new EntityAlreadyHasEnderecoException("Fornecedor", "já possui um endereço cadastrado!");
                break;
            case Entidade.CLIENTE:
                clientePossuiEnderecoRepository.findByCepAndId_Cliente(
                    enderecoRequest.cep(),
                    getOneClienteByEmail(email).getId()
                ).orElseThrow(() -> new EntityAlreadyHasEnderecoException("Cliente"));
        }
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity<ViaCepDTO> getFields(String cep) {
        return ResponseEntity.ok(getViaCepObject(cep));
    }
    
    private void createEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteByEmail(enderecoRequest.email());
        
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), cliente.getId());
        if (cpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Cliente");
        
        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void createEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), funcionario.getId());
        if (fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Funcionário");
        
        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
        
    }
    private void createEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        if (fornecedor.getEndereco() != null)
            throw new EntityAlreadyHasEnderecoException("Fornecedor", "já possui um endereço cadastrado!");
        
        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }
    
    private void updateEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), cliente.getId());
        if (cpeOptional.isEmpty()) throw new EnderecoNotFoundException(Entidade.CLIENTE);
        
        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<FuncionarioPossuiEndereco> cpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), funcionario.getId());
        if (cpeOptional.isEmpty()) throw new EnderecoNotFoundException(Entidade.FUNCIONARIO);
        
        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        if (fornecedor.getEndereco() == null) throw new EnderecoNotFoundException(Entidade.FORNECEDOR);
        
        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }
    
    private void removeEnderecoCliente(EnderecoRemoveRequest enderecoRequest) {
        Cliente cliente = getOneClienteByEmail(enderecoRequest.email());
        ClientePossuiEndereco cpe = clientePossuiEnderecoRepository.findByCepAndId_Cliente(
            enderecoRequest.cep(),
            cliente.getId()
        ).orElseThrow(() -> new EnderecoNotFoundException(Entidade.CLIENTE));
        clientePossuiEnderecoRepository.delete(cpe);
    }
    private void removeEnderecoFuncionario(EnderecoRemoveRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioByEmail(enderecoRequest.email());
        FuncionarioPossuiEndereco fpe = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(
            enderecoRequest.cep(),
            funcionario.getId()
        ).orElseThrow(() -> new EnderecoNotFoundException(Entidade.FUNCIONARIO));
        funcionarioPossuiEnderecoRepository.delete(fpe);
    }
    private void removeEnderecoFornecedor(EnderecoRemoveRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorByEmail(enderecoRequest.email());
        
        if (!fornecedor.getEndereco().getCep().equals(enderecoRequest.cep()))
            throw new EnderecoNotFoundException(Entidade.FORNECEDOR);
        
        fornecedor.setEndereco(null);
        fornecedorRepository.save(fornecedor);
    }
}