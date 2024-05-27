package com.lemnos.server.utils;

import com.lemnos.server.exceptions.cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.exceptions.endereco.CepNotValidException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EstadoNotFoundException;
import com.lemnos.server.exceptions.entidades.cliente.CnpjNotValidException;
import com.lemnos.server.exceptions.entidades.cliente.CpfNotValidException;
import com.lemnos.server.exceptions.global.TelefoneNotValidException;
import com.lemnos.server.exceptions.viacep.ViaCepNetworkException;
import com.lemnos.server.exceptions.viacep.ViaCepServerDownException;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Cidade;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Estado;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.viacep.ViaCep;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.repositories.endereco.possui.ClientePossuiEnderecoRepository;
import com.lemnos.server.repositories.endereco.possui.FuncionarioPossuiEnderecoRepository;
import io.micrometer.common.util.StringUtils;
import com.lemnos.server.repositories.endereco.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Util {
    @Autowired private EnderecoRepository enderecoRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private EstadoRepository estadoRepository;

    @Autowired protected ClientePossuiEnderecoRepository clientePossuiEnderecoRepository;
    @Autowired protected FuncionarioPossuiEnderecoRepository funcionarioPossuiEnderecoRepository;

    protected Date convertData(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada;
        try{
            dataFormatada = formatter.parse(data);
        }catch (ParseException e){
            throw new CadastroWrongDataFormatException(Codigo.GLOBAL);
        }
        return dataFormatada;
    }
    protected Long convertStringToLong(String obj, Codigo codigo) {
        switch (codigo){
            case Codigo.CEP:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new CepNotValidException("CEP inválido: utilize só números");
                }
            case Codigo.CPF:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new CpfNotValidException("CPF inválido: utilize só números");
                }
            case Codigo.TELEFONE:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new TelefoneNotValidException("Telefone inválido: utilize só números");
                }
            case Codigo.CNPJ:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new CnpjNotValidException("CNPJ inválido: utilize só números");
                }
            default:
                return 0L;
        }
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

    @Autowired private RestTemplate restTemplate;

    public ViaCepDTO getViaCepObject(String cep){
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