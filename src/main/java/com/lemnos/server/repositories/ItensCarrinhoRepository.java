package com.lemnos.server.repositories;

import com.lemnos.server.models.carrinho.ItensCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensCarrinhoRepository extends JpaRepository<ItensCarrinho, Integer> { }
