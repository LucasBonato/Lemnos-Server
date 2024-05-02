package com.lemnos.server.Utils;

import com.lemnos.server.Exceptions.Cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.Exceptions.Endereco.CepNotValidException;
import com.lemnos.server.Exceptions.Endereco.EnderecoNotValidException;
import com.lemnos.server.Exceptions.Endereco.EstadoNotFoundException;
import com.lemnos.server.Exceptions.Global.CnpjNotValidException;
import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import com.lemnos.server.Exceptions.Global.TelefoneNotValidException;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.Endereco.Cidade;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Endereco.Estado;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Util {
    protected final String CEP = "CEP";
    protected final String CPF = "CPF";
    protected final String TELEFONE = "TELEFONE";
    protected final String CNPJ = "CNPJ";

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

    protected Long convertStringToLong(String obj, String tipo) {
        switch (tipo){
            case CEP:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new CepNotValidException("CEP inválido: utilize só números");
                }
            case CPF:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new CpfNotValidException("CPF inválido: utilize só números");
                }
            case TELEFONE:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new TelefoneNotValidException("Telefone inválido: utilize só números");
                }
            case CNPJ:
                try {
                    return Long.parseLong(obj);
                } catch (NumberFormatException e) {
                    throw new CnpjNotValidException("CNPJ inválido: utilize só números");
                }
            default:
                return 0L;
        }
    }


    @Autowired private EnderecoRepository enderecoRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private EstadoRepository estadoRepository;

    @Autowired protected ClientePossuiEnderecoRepository clientePossuiEnderecoRepository;
    @Autowired protected FuncionarioPossuiEnderecoRepository funcionarioPossuiEnderecoRepository;

    protected Endereco getEndereco(EnderecoDTO enderecoDTO) {
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
