package com.lemnos.server.models.viacep;

import com.lemnos.server.exceptions.endereco.EnderecoNotValidException;
import com.lemnos.server.models.enums.Codigo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Data
public class ViaCep {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;


    @Autowired private static RestTemplate restTemplate;
    public static ViaCepDTO getViaCepObject(String cep){
        try {
            ViaCep viaCep = restTemplate.getForObject("https://viacep.com.br/ws/{cep}/json", ViaCep.class, cep);
            if(viaCep == null) return null;
            return new ViaCepDTO(viaCep.getCep().replace("-", ""), viaCep.getLogradouro(), viaCep.getLocalidade(), viaCep.getBairro(), viaCep.getUf());
        } catch (Exception e) {
            throw new EnderecoNotValidException(Codigo.CEP.ordinal() ,"Cep não existe!");
        }
    }
}
