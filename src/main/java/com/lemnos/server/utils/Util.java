package com.lemnos.server.utils;

import com.lemnos.server.exceptions.cadastro.CadastroWrongDataFormatException;
import com.lemnos.server.exceptions.endereco.CepNotValidException;
import com.lemnos.server.exceptions.entidades.cliente.CnpjNotValidException;
import com.lemnos.server.exceptions.entidades.cliente.CpfNotValidException;
import com.lemnos.server.exceptions.global.TelefoneNotValidException;
import com.lemnos.server.models.enums.Codigo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static Date convertData(String data) {
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