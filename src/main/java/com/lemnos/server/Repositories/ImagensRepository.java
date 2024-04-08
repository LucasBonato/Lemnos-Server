package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Produto.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagensRepository extends JpaRepository<Imagem, Integer> {
}
