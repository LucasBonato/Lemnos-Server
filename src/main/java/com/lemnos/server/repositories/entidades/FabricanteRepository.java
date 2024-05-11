package com.lemnos.server.repositories.entidades;

import com.lemnos.server.models.produto.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FabricanteRepository extends JpaRepository<Fabricante, Integer> {
    Optional<Fabricante> findByFabricante(String fabricante);
}
