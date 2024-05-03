package com.lemnos.server.Services;

import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Repositories.EnderecoRepository;
import com.lemnos.server.Utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService extends Util {
    @Autowired private EnderecoRepository enderecoRepository;

    public ResponseEntity<Void> update(EnderecoDTO enderecoDTO) {
        Endereco endereco = getEndereco(enderecoDTO);
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.getCidade().setCidade(enderecoDTO.getCidade());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.getEstado().setUf(enderecoDTO.getUf());

        enderecoRepository.save(endereco);

        return ResponseEntity.ok().build();
    }
}
