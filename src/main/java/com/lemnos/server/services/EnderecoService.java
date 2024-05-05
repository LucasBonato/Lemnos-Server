package com.lemnos.server.services;

import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.repositories.endereco.EnderecoRepository;
import com.lemnos.server.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService extends Util {
    @Autowired private EnderecoRepository enderecoRepository;

    public ResponseEntity<Void> update(EnderecoRequest enderecoRequest) {
        Endereco endereco = getEndereco(enderecoRequest);
        endereco.setLogradouro(enderecoRequest.logradouro());
        endereco.getCidade().setCidade(enderecoRequest.cidade());
        endereco.setBairro(enderecoRequest.bairro());
        endereco.getEstado().setUf(enderecoRequest.uf());

        enderecoRepository.save(endereco);

        return ResponseEntity.ok().build();
    }
}
