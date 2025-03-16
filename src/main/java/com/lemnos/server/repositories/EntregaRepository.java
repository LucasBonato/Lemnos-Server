package com.lemnos.server.repositories;

import com.lemnos.server.models.pedido.Entrega;
import com.lemnos.server.models.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    Optional<Entrega> findByPedido(Pedido pedido);
}
