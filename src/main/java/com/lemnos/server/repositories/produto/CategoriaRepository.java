package com.lemnos.server.repositories.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaRepository, Integer>{
    Optional<CategoriaRepository> findByNome(String nome);
}
