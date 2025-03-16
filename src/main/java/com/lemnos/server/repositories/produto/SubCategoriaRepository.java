package com.lemnos.server.repositories.produto;

import com.lemnos.server.models.produto.categoria.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Integer> {
    Optional<SubCategoria> findBySubCategoria(String nome);
}
