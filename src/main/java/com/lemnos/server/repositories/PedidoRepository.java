package com.lemnos.server.repositories;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCadastro(Cadastro cadastro);
}
