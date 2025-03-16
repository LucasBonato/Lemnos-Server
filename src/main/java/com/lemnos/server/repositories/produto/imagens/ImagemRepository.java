package com.lemnos.server.repositories.produto.imagens;

import com.lemnos.server.models.produto.imagens.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Integer> {
}
