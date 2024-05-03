package com.lemnos.server.Utils;

import com.lemnos.server.Exceptions.Cadastro.CadastroNotValidException;
import com.lemnos.server.Exceptions.Cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.Exceptions.Cliente.ClienteNotFoundException;
import com.lemnos.server.Exceptions.Endereco.CepNotValidException;
import com.lemnos.server.Exceptions.Endereco.EnderecoNotValidException;
import com.lemnos.server.Exceptions.Endereco.EstadoNotFoundException;
import com.lemnos.server.Exceptions.Fornecedor.FornecedorNotFoundException;
import com.lemnos.server.Exceptions.Funcionario.FuncionarioNotFoundException;
import com.lemnos.server.Exceptions.Global.CnpjNotValidException;
import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import com.lemnos.server.Exceptions.Global.TelefoneNotValidException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.Endereco.Cidade;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Endereco.Estado;
import com.lemnos.server.Models.Endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Models.Records.EnderecoRecord;
import com.lemnos.server.Repositories.*;
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
    protected static List<EnderecoRecord> getEnderecoRecords(Cliente cliente) {
        List<EnderecoRecord> enderecoRecords = new ArrayList<>();
        for(ClientePossuiEndereco cpe : cliente.getEnderecos()){
            EnderecoRecord enderecoRecord = new EnderecoRecord(
                    cpe.getEndereco().getCep(),
                    cpe.getEndereco().getLogradouro(),
                    cpe.getNumeroLogradouro(),
                    cpe.getComplemento(),
                    cpe.getEndereco().getBairro(),
                    cpe.getEndereco().getCidade().getCidade(),
                    cpe.getEndereco().getEstado().getUf()
            );
            enderecoRecords.add(enderecoRecord);
        }
        return enderecoRecords;
    }
    protected static List<EnderecoRecord> getEnderecoRecords(Funcionario funcionario) {
        List<EnderecoRecord> enderecoRecords = new ArrayList<>();
        for(FuncionarioPossuiEndereco fpe : funcionario.getEnderecos()){
            EnderecoRecord enderecoRecord = new EnderecoRecord(
                    fpe.getEndereco().getCep(),
                    fpe.getEndereco().getLogradouro(),
                    fpe.getNumeroLogradouro(),
                    fpe.getComplemento(),
                    fpe.getEndereco().getBairro(),
                    fpe.getEndereco().getCidade().getCidade(),
                    fpe.getEndereco().getEstado().getUf()
            );
            enderecoRecords.add(enderecoRecord);
        }
        return enderecoRecords;
    }
    protected static EnderecoRecord getEnderecoRecords(Fornecedor fornecedor) {
        if(fornecedor.getEndereco() == null) return null;
        return new EnderecoRecord(
                fornecedor.getEndereco().getCep(),
                fornecedor.getEndereco().getLogradouro(),
                fornecedor.getNumeroLogradouro(),
                fornecedor.getComplemento(),
                fornecedor.getEndereco().getBairro(),
                fornecedor.getEndereco().getCidade().getCidade(),
                fornecedor.getEndereco().getEstado().getUf()
        );
    }
    private void verificarCamposEndereco(EnderecoDTO enderecoDTO) {
        if(enderecoDTO.getLogradouro().isBlank() || enderecoDTO.getBairro().isBlank() || enderecoDTO.getCidade().isBlank() || enderecoDTO.getUf().isBlank() || enderecoDTO.getNumeroLogradouro() == null){
            throw new EnderecoNotValidException(Codigo.GLOBAL.ordinal(), "Todos os campos de endereço são obrigatórios!");
        }
        if(enderecoDTO.getLogradouro().length() < 2 || enderecoDTO.getLogradouro().length() > 50){
            throw new EnderecoNotValidException(Codigo.LOGRADOURO.ordinal(), "Logradouro precisa estar entre 2 e 50 caracteres!");
        }
        if(enderecoDTO.getComplemento().length() > 20){
            throw new EnderecoNotValidException(Codigo.COMPLEMENTO.ordinal(), "Complemento só pode possui até 20 caracteres!");
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