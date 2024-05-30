package com.lemnos.server.utils;

import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EstadoNotFoundException;
import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.entidades.fornecedor.FornecedorNotFoundException;
import com.lemnos.server.exceptions.entidades.funcionario.FuncionarioNotFoundException;
import com.lemnos.server.exceptions.viacep.ViaCepNetworkException;
import com.lemnos.server.exceptions.viacep.ViaCepServerDownException;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Cidade;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Estado;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.viacep.ViaCep;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.repositories.endereco.CidadeRepository;
import com.lemnos.server.repositories.endereco.EnderecoRepository;
import com.lemnos.server.repositories.endereco.EstadoRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.*;

import java.util.Optional;

public class UtilEndereco {
    @Autowired private RestTemplate restTemplate;
    @Autowired private EnderecoRepository enderecoRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private EstadoRepository estadoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private FornecedorRepository fornecedorRepository;

    protected Cliente getOneClienteById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(ClienteNotFoundException::new);
    }
    protected Funcionario getOneFuncionarioById(Integer id) {
        return funcionarioRepository.findById(id).orElseThrow(FuncionarioNotFoundException::new);
    }
    protected Fornecedor getOneFornecedorById(Integer id) {
        return fornecedorRepository.findById(id).orElseThrow(FornecedorNotFoundException::new);
    }

    protected Endereco getEndereco(EnderecoRequest enderecoRequest) {
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

    private ViaCepDTO getViaCepObject(String cep){
        try {
            ViaCep viaCep = restTemplate.getForObject("https://viacep.com.br/ws/{cep}/json", ViaCep.class, cep);
            if(viaCep == null) return null;
            return new ViaCepDTO(viaCep.getCep().replace("-", ""), viaCep.getLogradouro(), viaCep.getLocalidade(), viaCep.getBairro(), viaCep.getUf());
        } catch (HttpClientErrorException e) {
            throw new EnderecoNotValidException(Codigo.CEP, "CEP inexistente!");
        } catch (HttpServerErrorException e) {
            throw new ViaCepServerDownException();
        } catch (ResourceAccessException e) {
            throw new ViaCepNetworkException("Problema de rede ao acessar o serviço ViaCep");
        } catch (RestClientException e) {
            throw new RuntimeException("Erro no RestTemplate, consulte um desenvolvedor!");
        }
    }

    private Endereco cadastrarNovoEndereco(ViaCepDTO viaCep, EnderecoRequest enderecoRequest) {
        verificarCamposEndereco(enderecoRequest);

        Optional<Cidade> cidadeOptional = cidadeRepository.findByCidade(viaCep.cidade());
        Cidade cidade = cidadeOptional.orElseGet(() -> cidadeRepository.save(new Cidade(viaCep.cidade())));

        Estado estado = estadoRepository.findByUf(viaCep.uf()).orElseThrow(EstadoNotFoundException::new);

        Endereco endereco = new Endereco(viaCep, cidade, estado);
        return enderecoRepository.save(endereco);
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
}
