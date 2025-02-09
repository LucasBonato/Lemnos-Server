package com.lemnos.server.repositories.produto;

import com.lemnos.server.models.produto.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> { }
