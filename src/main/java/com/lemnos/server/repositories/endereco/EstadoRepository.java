package com.lemnos.server.repositories.endereco;

import com.lemnos.server.models.endereco.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByUf(String uf);
}
