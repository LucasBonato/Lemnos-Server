package com.lemnos.server.Services;

import com.lemnos.server.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired private ClienteRepository clienteRepository;
}
