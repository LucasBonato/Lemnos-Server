package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByUf(String uf);
}
