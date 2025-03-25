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
import com.lemnos.server.models.endereco.possui.ClientePossuiEndereco;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Estado;
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
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
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
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    
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
        Endereco endereco = getEndereco(enderecoRequest);
        boolean enderecoJaExiste;
        
        switch (enderecoRequest.entidade()) {
            case Entidade.FUNCIONARIO:
                Funcionario funcionario = entityService.getOneFuncionarioByEmail(email);
                
                enderecoJaExiste = funcionario.getEnderecos().stream()
                        .anyMatch(fpe -> fpe.getEndereco().getCep().equals(endereco.getCep()));
                
                if (enderecoJaExiste) {
                    throw new EntityAlreadyHasEnderecoException(Entidade.FUNCIONARIO);
                }
                break;
            case Entidade.FORNECEDOR:
                if (entityService.getOneFornecedorByEmail(email).getEndereco() != null)
                    throw new EntityAlreadyHasEnderecoException(Entidade.FORNECEDOR, "já possui um endereço cadastrado!");
                break;
            case Entidade.CLIENTE:
                Cliente cliente = entityService.getOneClienteByEmail(enderecoRequest.email());
                
                enderecoJaExiste = cliente.getEnderecos().stream()
                        .anyMatch(cpe -> cpe.getEndereco().getCep().equals(endereco.getCep()));
                
                if (enderecoJaExiste) {
                    throw new EntityAlreadyHasEnderecoException(Entidade.CLIENTE);
                }
                break;
        }
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity<ViaCepDTO> getFields(String cep) {
        return ResponseEntity.ok(getViaCepObject(cep));
    }
    
    private void createEnderecoCliente(EnderecoRequest enderecoRequest) {
        Cliente cliente = entityService.getOneClienteByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        boolean enderecoJaExiste = cliente.getEnderecos().stream()
                .anyMatch(cpe -> cpe.getEndereco().getCep().equals(endereco.getCep()));
        
        if (enderecoJaExiste) {
            throw new EntityAlreadyHasEnderecoException(Entidade.CLIENTE);
        }
        
        ClientePossuiEndereco clientePossuiEndereco = new ClientePossuiEndereco(
                cliente,
                endereco,
                enderecoRequest.numeroLogradouro(),
                enderecoRequest.complemento()
        );
        
        cliente.getEnderecos().add(clientePossuiEndereco);
        clienteRepository.save(cliente);
    }
    private void createEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = entityService.getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
        
        boolean enderecoJaExiste = funcionario.getEnderecos().stream()
                .anyMatch(cpe -> cpe.getEndereco().getCep().equals(endereco.getCep()));
        
        if (enderecoJaExiste) {
            throw new EntityAlreadyHasEnderecoException(Entidade.CLIENTE);
        }
        
        FuncionarioPossuiEndereco funcionarioPossuiEndereco = new FuncionarioPossuiEndereco(
                funcionario,
                endereco,
                enderecoRequest.numeroLogradouro(),
                enderecoRequest.complemento()
        );
        
        funcionario.getEnderecos().add(funcionarioPossuiEndereco);
        funcionarioRepository.save(funcionario);
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
        
        ClientePossuiEndereco clientePossuiEndereco = cliente.getEnderecos().stream()
                .filter(cpe -> cpe.getEndereco().getCep().equals(endereco.getCep()))
                .findFirst()
                .orElseThrow(() -> new EnderecoNotFoundException(Entidade.CLIENTE));
        
        clientePossuiEndereco.setComplemento(enderecoRequest.complemento());
        clientePossuiEndereco.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        
        clienteRepository.save(cliente);
    }
    private void updateEnderecoFuncionario(EnderecoRequest enderecoRequest) {
        Funcionario funcionario = entityService.getOneFuncionarioByEmail(enderecoRequest.email());
        Endereco endereco = getEndereco(enderecoRequest);
     
        FuncionarioPossuiEndereco funcionarioPossuiEndereco = funcionario.getEnderecos().stream()
                .filter(fpe -> fpe.getEndereco().getCep().equals(endereco.getCep()))
                .findFirst()
                .orElseThrow(() -> new EntityAlreadyHasEnderecoException(Entidade.FUNCIONARIO));
        
        funcionarioPossuiEndereco.setComplemento(enderecoRequest.complemento());
        funcionarioPossuiEndereco.setNumeroLogradouro(enderecoRequest.numeroLogradouro());
        
        funcionarioRepository.save(funcionario);
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
        
        ClientePossuiEndereco clientePossuiEndereco = cliente.getEnderecos().stream()
                .filter(cpe -> cpe.getEndereco().getCep().equals(enderecoRequest.cep()))
                .findFirst()
                .orElseThrow(() -> new EnderecoNotFoundException(Entidade.CLIENTE));
        
        cliente.getEnderecos().remove(clientePossuiEndereco);
        
        clienteRepository.save(cliente);
    }
    private void removeEnderecoFuncionario(EnderecoRemoveRequest enderecoRequest) {
        Funcionario funcionario = entityService.getOneFuncionarioByEmail(enderecoRequest.email());
        
        FuncionarioPossuiEndereco funcionarioPossuiEndereco = funcionario.getEnderecos().stream()
                .filter(fpe -> fpe.getEndereco().getCep().equals(enderecoRequest.cep()))
                .findFirst()
                .orElseThrow(() -> new EnderecoNotFoundException(Entidade.CLIENTE));
        
        funcionario.getEnderecos().remove(funcionarioPossuiEndereco);
        
        funcionarioRepository.save(funcionario);
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