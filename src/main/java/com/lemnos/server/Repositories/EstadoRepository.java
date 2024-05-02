package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByUf(String uf);
}
