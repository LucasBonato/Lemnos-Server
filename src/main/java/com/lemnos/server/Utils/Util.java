package com.lemnos.server.Utils;

import com.lemnos.server.Exceptions.Cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.Exceptions.Endereco.CepNotValidException;
import com.lemnos.server.Exceptions.Global.CnpjNotValidException;
import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import com.lemnos.server.Exceptions.Global.TelefoneNotValidException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Funcionario;
import com.lemnos.server.Models.Records.EnderecoRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    protected static List<EnderecoRecord> getEnderecoRecords(Cliente cliente) {
        List<EnderecoRecord> enderecoRecords = new ArrayList<>();
        for(ClientePossuiEndereco cpe : cliente.getEnderecos()){
            EnderecoRecord enderecoRecord = new EnderecoRecord(
                    cpe.getEndereco().getCep(),
                    cpe.getEndereco().getLogradouro(),
                    cpe.getNumeroLogradouro(),
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
                    fpe.getEndereco().getBairro(),
                    fpe.getEndereco().getCidade().getCidade(),
                    fpe.getEndereco().getEstado().getUf()
            );
            enderecoRecords.add(enderecoRecord);
        }
        return enderecoRecords;
    }
    protected static EnderecoRecord getEnderecoRecords(Fornecedor fornecedor) {
        return new EnderecoRecord(
                fornecedor.getEndereco().getCep(),
                fornecedor.getEndereco().getLogradouro(),
                fornecedor.getNumeroLogradouro(),
                fornecedor.getEndereco().getBairro(),
                fornecedor.getEndereco().getCidade().getCidade(),
                fornecedor.getEndereco().getEstado().getUf()
        );
    }
}
