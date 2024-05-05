package com.lemnos.server.utils;

import com.lemnos.server.exceptions.cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.exceptions.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.endereco.CepNotValidException;
import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.exceptions.endereco.EstadoNotFoundException;
import com.lemnos.server.exceptions.fornecedor.FornecedorNotFoundException;
import com.lemnos.server.exceptions.funcionario.FuncionarioNotFoundException;
import com.lemnos.server.exceptions.cliente.CnpjNotValidException;
import com.lemnos.server.exceptions.cliente.CpfNotValidException;
import com.lemnos.server.exceptions.global.TelefoneNotValidException;
import com.lemnos.server.models.Cliente;
import com.lemnos.server.models.endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Cidade;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Estado;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.Fornecedor;
import com.lemnos.server.models.Funcionario;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.repositories.*;
import com.lemnos.server.repositories.endereco.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Util {
    @Autowired private EnderecoRepository enderecoRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private EstadoRepository estadoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private FornecedorRepository fornecedorRepository;

    @Autowired protected ClientePossuiEnderecoRepository clientePossuiEnderecoRepository;
    @Autowired protected FuncionarioPossuiEnderecoRepository funcionarioPossuiEnderecoRepository;

    protected final String CEP = "CEP";
    protected final String CPF = "CPF";
    protected final String TELEFONE = "TELEFONE";
    protected final String CNPJ = "CNPJ";

    protected Endereco getEndereco(EnderecoRequest enderecoRequest) {
        String cep = enderecoRequest.cep();
        Endereco endereco;

        Optional<Endereco> optionalEndereco = enderecoRepository.findById(cep);
        if(optionalEndereco.isEmpty()){
            verificarCamposEndereco(enderecoRequest);
            endereco = cadastrarNovoEndereco(cep, enderecoRequest);
        } else {
            endereco = optionalEndereco.get();
        }
        return endereco;
    }
    protected static List<EnderecoResponse> getEnderecoRecords(Cliente cliente) {
        List<EnderecoResponse> enderecoResponses = new ArrayList<>();
        for(ClientePossuiEndereco cpe : cliente.getEnderecos()){
            EnderecoResponse enderecoResponse = new EnderecoResponse(
                    cpe.getEndereco().getCep(),
                    cpe.getEndereco().getLogradouro(),
                    cpe.getNumeroLogradouro(),
                    cpe.getComplemento(),
                    cpe.getEndereco().getBairro(),
                    cpe.getEndereco().getCidade().getCidade(),
                    cpe.getEndereco().getEstado().getUf()
            );
            enderecoResponses.add(enderecoResponse);
        }
        return enderecoResponses;
    }
    protected static List<EnderecoResponse> getEnderecoRecords(Funcionario funcionario) {
        List<EnderecoResponse> enderecoResponses = new ArrayList<>();
        for(FuncionarioPossuiEndereco fpe : funcionario.getEnderecos()){
            EnderecoResponse enderecoResponse = new EnderecoResponse(
                    fpe.getEndereco().getCep(),
                    fpe.getEndereco().getLogradouro(),
                    fpe.getNumeroLogradouro(),
                    fpe.getComplemento(),
                    fpe.getEndereco().getBairro(),
                    fpe.getEndereco().getCidade().getCidade(),
                    fpe.getEndereco().getEstado().getUf()
            );
            enderecoResponses.add(enderecoResponse);
        }
        return enderecoResponses;
    }
    protected static EnderecoResponse getEnderecoRecords(Fornecedor fornecedor) {
        if(fornecedor.getEndereco() == null) return null;
        return new EnderecoResponse(
                fornecedor.getEndereco().getCep(),
                fornecedor.getEndereco().getLogradouro(),
                fornecedor.getNumeroLogradouro(),
                fornecedor.getComplemento(),
                fornecedor.getEndereco().getBairro(),
                fornecedor.getEndereco().getCidade().getCidade(),
                fornecedor.getEndereco().getEstado().getUf()
        );
    }
    private void verificarCamposEndereco(EnderecoRequest enderecoRequest) {
        if(enderecoRequest.logradouro().isBlank() || enderecoRequest.bairro().isBlank() || enderecoRequest.cidade().isBlank() || enderecoRequest.uf().isBlank() || enderecoRequest.numeroLogradouro() == null){
            throw new EnderecoNotValidException(Codigo.GLOBAL.ordinal(), "Todos os campos de endereço são obrigatórios!");
        }
        if(enderecoRequest.logradouro().length() < 2 || enderecoRequest.logradouro().length() > 50){
            throw new EnderecoNotValidException(Codigo.LOGRADOURO.ordinal(), "Logradouro precisa estar entre 2 e 50 caracteres!");
        }
        if(enderecoRequest.complemento().length() > 20){
            throw new EnderecoNotValidException(Codigo.COMPLEMENTO.ordinal(), "Complemento só pode possui até 20 caracteres!");
        }
        if(enderecoRequest.cidade().length() < 2 || enderecoRequest.cidade().length() > 30){
            throw new EnderecoNotValidException(Codigo.CIDADE.ordinal(), "Cidade precisa estar entre 2 e 30 caracteres!");
        }
        if(enderecoRequest.bairro().length() < 2 || enderecoRequest.bairro().length() > 30){
            throw new EnderecoNotValidException(Codigo.BAIRRO.ordinal(), "Bairro precisa estar entre 2 e 30 caracteres!");
        }
        if(enderecoRequest.uf().length() != 2){
            throw new EnderecoNotValidException(Codigo.UF.ordinal(), "O UF precisa ter 2 caracteres!");
        }
        if(enderecoRequest.numeroLogradouro() < 0 || enderecoRequest.numeroLogradouro() > 9999){
            throw new EnderecoNotValidException(Codigo.NUMERO_LOGRADOURO.ordinal(), "O número de Logradouro não pode ser negativo ou maior que 9999");
        }
    }
    private Endereco cadastrarNovoEndereco(String cep, EnderecoRequest enderecoRequest) {
        Cidade cidade;

        enderecoRequest = enderecoRequest.setBairro(enderecoRequest.bairro().toLowerCase());
        enderecoRequest = enderecoRequest.setCidade(enderecoRequest.cidade().toLowerCase());
        enderecoRequest = enderecoRequest.setUf(enderecoRequest.uf().toUpperCase());

        Optional<Cidade> cidadeOptional = cidadeRepository.findByCidade(enderecoRequest.cidade());
        if(cidadeOptional.isEmpty()){
            Cidade novaCidade = new Cidade(enderecoRequest.cidade());
            cidade = cidadeRepository.save(novaCidade);
        } else {
            cidade = cidadeOptional.get();
        }
        Estado estado = estadoRepository.findByUf(enderecoRequest.uf()).orElseThrow(EstadoNotFoundException::new);

        Endereco endereco = new Endereco(cep, cidade, estado, enderecoRequest);
        return enderecoRepository.save(endereco);
    }

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

    protected Fornecedor getOneFornecedorById(Integer id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if(fornecedorOptional.isPresent()){
            return fornecedorOptional.get();
        }
        throw new FornecedorNotFoundException();
    }
    protected Funcionario getOneFuncionarioById(Integer id) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if(funcionarioOptional.isPresent()){
            return funcionarioOptional.get();
        }
        throw new FuncionarioNotFoundException();
    }
    protected Cliente getOneClienteById(Integer id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if(clienteOptional.isPresent()){
            return clienteOptional.get();
        }
        throw new ClienteNotFoundException();
    }
}