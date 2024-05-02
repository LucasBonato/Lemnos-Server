package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, String> { }
