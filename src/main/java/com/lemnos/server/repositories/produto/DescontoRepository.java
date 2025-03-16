package com.lemnos.server.repositories.produto;

import com.lemnos.server.models.produto.Desconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DescontoRepository extends JpaRepository<Desconto, Integer> {
    Optional<Desconto> findByValorDesconto(String valor);
}
