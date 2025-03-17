package com.lemnos.server.services;

import com.lemnos.server.exceptions.endereco.EnderecoNotFoundException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.exceptions.endereco.EstadoNotFoundException;
import com.lemnos.server.exceptions.viacep.RestTemplateException;
import com.lemnos.server.exceptions.viacep.ViaCepNetworkException;
import com.lemnos.server.exceptions.viacep.ViaCepServerDownException;
import com.lemnos.server.models.dtos.requests.EnderecoRemoveRequest;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Cidade;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Estado;
import com.lemnos.server.models.endereco.possui.ClientePossuiEndereco;
import com.lemnos.server.models.endereco.possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Entidade;
import com.lemnos.server.models.viacep.ViaCep;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.repositories.endereco.CidadeRepository;
import com.lemnos.server.repositories.endereco.EnderecoRepository;
import com.lemnos.server.repositories.endereco.EstadoRepository;
import com.lemnos.server.repositories.endereco.possui.ClientePossuiEnderecoRepository;
import com.lemnos.server.repositories.endereco.possui.FuncionarioPossuiEnderecoRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final RestTemplate restTemplate;
    private final EntityService entityService;
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
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
                    entityService.getOneFuncionarioByEmail(email).getId()
                ).orElseThrow(() -> new EntityAlreadyHasEnderecoException(Entidade.FUNCIONARIO));
                break;
            case Entidade.FORNECEDOR:
                if (entityService.getOneFornecedorByEmail(email).getEndereco() != null)
                    throw new EntityAlreadyHasEnderecoException(Entidade.FORNECEDOR, "já possui um endereço cadastrado!");
                break;
            case Entidade.CLIENTE:
                clientePossuiEnderecoRepository.findByCepAndId_Cliente(
                    enderecoRequest.cep(),
                        entityService.getOneClienteByEmail(email).getId()
                ).orElseThrow(() -> new EntityAlreadyHasEnderecoException(Entidade.CLIENTE));
        }
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity<ViaCepDTO> getFields(String cep) {
        return ResponseEntity.ok(getViaCepObject(cep));
    }
    
    private void createEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = entityService.getOneClienteByEmail(enderecoRequest.email());
        
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), cliente.getId());
        if (cpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException(Entidade.CLIENTE);
        
        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void createEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = entityService.getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), funcionario.getId());
        if (fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException(Entidade.FUNCIONARIO);
        
        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
        
    }
    private void createEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = entityService.getOneFornecedorByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        if (fornecedor.getEndereco() != null)
            throw new EntityAlreadyHasEnderecoException(Entidade.FORNECEDOR, "já possui um endereço cadastrado!");
        
        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }
    
    private void updateEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = entityService.getOneClienteByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<ClientePossuiEndereco> cpeOptional = clientePossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), cliente.getId());
        if (cpeOptional.isEmpty()) throw new EnderecoNotFoundException(Entidade.CLIENTE);
        
        clientePossuiEnderecoRepository.save(new ClientePossuiEndereco(cliente, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = entityService.getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        Optional<FuncionarioPossuiEndereco> cpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), funcionario.getId());
        if (cpeOptional.isEmpty()) throw new EnderecoNotFoundException(Entidade.FUNCIONARIO);
        
        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));
    }
    private void updateEnderecoFornecedor(EnderecoRequest enderecoRequest) {
        Fornecedor fornecedor = entityService.getOneFornecedorByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        if (fornecedor.getEndereco() == null) throw new EnderecoNotFoundException(Entidade.FORNECEDOR);
        
        fornecedor.setEndereco(endereco);
        fornecedor.setComplemento(enderecoRequest.complemento());
        fornecedor.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        fornecedorRepository.save(fornecedor);
    }
    
    private void removeEnderecoCliente(EnderecoRemoveRequest enderecoRequest) {
        Cliente cliente = entityService.getOneClienteByEmail(enderecoRequest.email());
        ClientePossuiEndereco cpe = clientePossuiEnderecoRepository.findByCepAndId_Cliente(
            enderecoRequest.cep(),
            cliente.getId()
        ).orElseThrow(() -> new EnderecoNotFoundException(Entidade.CLIENTE));
        clientePossuiEnderecoRepository.delete(cpe);
    }
    private void removeEnderecoFuncionario(EnderecoRemoveRequest enderecoRequest) {
        Funcionario funcionario = entityService.getOneFuncionarioByEmail(enderecoRequest.email());
        FuncionarioPossuiEndereco fpe = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(
            enderecoRequest.cep(),
            funcionario.getId()
        ).orElseThrow(() -> new EnderecoNotFoundException(Entidade.FUNCIONARIO));
        funcionarioPossuiEnderecoRepository.delete(fpe);
    }
    private void removeEnderecoFornecedor(EnderecoRemoveRequest enderecoRequest) {
        Fornecedor fornecedor = entityService.getOneFornecedorByEmail(enderecoRequest.email());
        
        if (!fornecedor.getEndereco().getCep().equals(enderecoRequest.cep()))
            throw new EnderecoNotFoundException(Entidade.FORNECEDOR);
        
        fornecedor.setEndereco(null);
        fornecedorRepository.save(fornecedor);
    }
    
    private void verificarCamposEndereco(EnderecoRequest enderecoRequest) {
        if(enderecoRequest.numeroLogradouro() == null){
            throw new EnderecoNotValidException(Codigo.NUMERO_LOGRADOURO, "O campo de número logradouro é obrigatório!");
        }
        if(enderecoRequest.numeroLogradouro() < 0 || enderecoRequest.numeroLogradouro() > 9999){
            throw new EnderecoNotValidException(Codigo.NUMERO_LOGRADOURO, "O número de Logradouro não pode ser negativo ou maior que 9999");
        }
        if(StringUtils.isNotBlank(enderecoRequest.complemento()) && enderecoRequest.complemento().length() > 20) {
            throw new EnderecoNotValidException(Codigo.COMPLEMENTO, "O complemento só pode possuir até 20 caracteres!");
        }
    }
    
    private ViaCepDTO getViaCepObject(String cep) {
        try {
            ViaCep viaCep = restTemplate.getForObject("https://viacep.com.br/ws/{cep}/json", ViaCep.class, cep);
            if(viaCep == null) return null;
            return new ViaCepDTO(viaCep.getCep().replace("-", ""), viaCep.getLogradouro(), viaCep.getLocalidade(), viaCep.getBairro(), viaCep.getUf());
        }
        catch (HttpClientErrorException e) {
            throw new EnderecoNotValidException(Codigo.CEP, "CEP inexistente!");
        }
        catch (HttpServerErrorException e) {
            throw new ViaCepServerDownException();
        }
        catch (ResourceAccessException e) {
            throw new ViaCepNetworkException("Problema de rede ao acessar o serviço ViaCep");
        }
        catch (RestClientException e) {
            throw new RestTemplateException("Erro no RestTemplate, consulte um desenvolvedor!");
        }
    }
    
    private Endereco getEndereco(EnderecoRequest enderecoRequest) {
        String cep = enderecoRequest.cep();
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(cep);
        if(optionalEndereco.isPresent()) {
            return optionalEndereco.get();
        }
        
        ViaCepDTO via = getViaCepObject(cep);
        if(via != null) {
            return optionalEndereco.orElseGet(() -> cadastrarNovoEndereco(via, enderecoRequest));
        }
        throw new EnderecoNotValidException(Codigo.CEP ,"Cep não existe!");
    }
    
    private Endereco cadastrarNovoEndereco(ViaCepDTO viaCep, EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);
        
        Optional<Cidade> cidadeOptional = cidadeRepository.findByCidade(viaCep.cidade());
        Cidade cidade = cidadeOptional.orElseGet(() -> cidadeRepository.save(new Cidade(viaCep.cidade())));
        
        Estado estado = estadoRepository.findByUf(viaCep.uf()).orElseThrow(EstadoNotFoundException::new);
        
        Endereco endereco = new Endereco(viaCep, cidade, estado);
        return enderecoRepository.save(endereco);
    }
}