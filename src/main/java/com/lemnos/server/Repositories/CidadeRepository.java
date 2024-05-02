package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Optional<Cidade> findByCidade(String cidade);
}
