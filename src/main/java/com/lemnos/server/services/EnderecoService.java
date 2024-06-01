package com.lemnos.server.services;

import com.lemnos.server.exceptions.endereco.EnderecoNotFoundException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.viacep.ViaCep;
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

    public ResponseEntity<Void> removeEndereco(String id, String cep, String entidade) {
        switch (entidade){
            case "funcionario":
                removeEnderecoFuncionario(Integer.getInteger(id), cep);
                break;
            case "fornecedor":
                removeEnderecoFornecedor(Integer.getInteger(id), cep);
                break;
            default:
                removeEnderecoCliente(Integer.getInteger(id), cep);
        }
        return ResponseEntity.ok().build();
    }


    private void createEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteById(enderecoRequest.id());

        Endereco endereco = getEndereco(enderecoRequest);

        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), enderecoRequest.id());
        if(cpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Cliente");

        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void createEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioById(enderecoRequest.id());
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), enderecoRequest.id());
        if(fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Funcionário");

        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));

    }
    private void createEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorById(enderecoRequest.id());
        Endereco endereco = getEndereco(enderecoRequest);

        if(fornecedor.getEndereco() != null) throw new EntityAlreadyHasEnderecoException("Fornecedor", "já possui um endereço cadastrado!");

        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }

    private void updateEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = getOneClienteById(enderecoRequest.id());
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), enderecoRequest.id());
        if(cpeOptional.isEmpty()) throw new EnderecoNotFoundException("Cliente");

        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioById(enderecoRequest.id());
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> cpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), enderecoRequest.id());
        if(cpeOptional.isEmpty()) throw new EnderecoNotFoundException("Cliente");

        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = getOneFornecedorById(enderecoRequest.id());
        Endereco endereco = getEndereco(enderecoRequest);

        if(fornecedor.getEndereco() == null) throw new EnderecoNotFoundException("Fornecedor");

        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }

    private void removeEnderecoCliente(Integer id, String cep) {
        getOneClienteById(id);
        ClientePossuiEndereco cpe = clientePossuiEnderecoRepository.findByCepAndId_Cliente(cep, id).orElseThrow(() -> new EnderecoNotFoundException("Cliente"));
        clientePossuiEnderecoRepository.delete(cpe);
    }
    private void removeEnderecoFornecedor(Integer id, String cep) {
        Fornecedor fornecedor = getOneFornecedorById(id);

        if(!fornecedor.getEndereco().getCep().equals(cep)) throw new EnderecoNotFoundException("Fornecedor");

        fornecedor.setEndereco(null);
        fornecedorRepository.save(fornecedor);
    }
    private void removeEnderecoFuncionario(Integer id, String cep) {
        getOneFuncionarioById(id);
        FuncionarioPossuiEndereco fpe = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(cep, id).orElseThrow(() -> new EnderecoNotFoundException("Funcionário"));
        funcionarioPossuiEnderecoRepository.delete(fpe);
    }

    public ResponseEntity<Void> verificarCampos(Integer id, EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);

        ViaCepDTO via = getViaCepObject(enderecoRequest.cep());
        if(via == null) throw new EnderecoNotValidException(Codigo.CEP ,"Cep não existe!");

        switch (enderecoRequest.entidade()){
            case "funcionario":
                Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(enderecoRequest.cep(), id);
                if(fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Funcionário");
                break;
            case "fornecedor":
                if(getOneFornecedorById(id).getEndereco() != null) throw new EntityAlreadyHasEnderecoException("Fornecedor", "já possui um endereço cadastrado!");
                break;
            default:
                Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(enderecoRequest.cep(), id);
                if(cpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Cliente");
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<ViaCepDTO> getFields(String cep) {
        return ResponseEntity.ok(getViaCepObject(cep));
    }
}