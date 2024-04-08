package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Produto.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvalicaoRepository extends JpaRepository<Avaliacao, Integer> {
}
