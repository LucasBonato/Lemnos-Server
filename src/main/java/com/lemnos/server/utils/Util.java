package com.lemnos.server.utils;

import com.lemnos.server.exceptions.cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.exceptions.endereco.CepNotValidException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EstadoNotFoundException;
import com.lemnos.server.exceptions.cliente.CnpjNotValidException;
import com.lemnos.server.exceptions.cliente.CpfNotValidException;
import com.lemnos.server.exceptions.global.TelefoneNotValidException;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Cidade;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Estado;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.viacep.ViaCep;
import com.lemnos.server.models.viacep.ViaCepDTO;
import com.lemnos.server.repositories.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
            throw new CadastroWrongDataFormatException(Codigo.GLOBAL.ordinal());
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

        ViaCepDTO via = ViaCep.getViaCepObject(cep);
        if(via != null) {
            return optionalEndereco.orElseGet(() -> cadastrarNovoEndereco(via, enderecoRequest));
        }
        throw new EnderecoNotValidException(Codigo.CEP.ordinal() ,"Cep não existe!");
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
            throw new EnderecoNotValidException(Codigo.NUMERO_LOGRADOURO.ordinal(), "O campo de número logradouro é obrigatório!");
        }
        if(enderecoRequest.numeroLogradouro() < 0 || enderecoRequest.numeroLogradouro() > 9999){
            throw new EnderecoNotValidException(Codigo.NUMERO_LOGRADOURO.ordinal(), "O número de Logradouro não pode ser negativo ou maior que 9999");
        }
        if(StringUtils.isNotBlank(enderecoRequest.complemento()) && enderecoRequest.complemento().length() > 20) {
            throw new EnderecoNotValidException(Codigo.COMPLEMENTO.ordinal(), "O complemento só pode possuir até 20 caracteres!");
        }
    }
}