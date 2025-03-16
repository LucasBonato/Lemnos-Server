package com.lemnos.server.repositories.produto;

import com.lemnos.server.models.produto.Avaliacao;
import com.lemnos.server.models.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findAllByProduto(Produto produto);
}
