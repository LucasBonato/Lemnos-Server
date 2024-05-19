package com.lemnos.server.repositories.produto.imagens;

import com.lemnos.server.models.produto.imagens.ImagemPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagemPrincipalRepository extends JpaRepository<ImagemPrincipal, Integer> {
    Optional<ImagemPrincipal> findByImagemPrincipal(String imagem);
}
