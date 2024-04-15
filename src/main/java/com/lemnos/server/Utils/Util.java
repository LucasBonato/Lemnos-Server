package com.lemnos.server.Utils;

import com.lemnos.server.Exceptions.Cadastro.CadastroWrongDataFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    protected Date convertData(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada;
        try{
            dataFormatada = formatter.parse(data);
        }catch (ParseException e){
            throw new CadastroWrongDataFormatException();
        }
        return dataFormatada;
    }
}
