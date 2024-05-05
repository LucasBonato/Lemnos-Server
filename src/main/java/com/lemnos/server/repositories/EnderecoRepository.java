package com.lemnos.server.repositories;

import com.lemnos.server.models.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> { }
