package com.lemnos.server.services;

import com.lemnos.server.repositories.endereco.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoManager {
    private final EnderecoRepository enderecoRepository;
}
