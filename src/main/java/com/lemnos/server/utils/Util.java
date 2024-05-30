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
}