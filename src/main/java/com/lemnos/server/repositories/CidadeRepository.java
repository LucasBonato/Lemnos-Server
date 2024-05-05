package com.lemnos.server.repositories;

import com.lemnos.server.models.endereco.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Optional<Cidade> findByCidade(String cidade);
}
