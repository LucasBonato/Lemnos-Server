package com.lemnos.server.repositories.produto;

import com.lemnos.server.models.produto.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Integer> {
}
