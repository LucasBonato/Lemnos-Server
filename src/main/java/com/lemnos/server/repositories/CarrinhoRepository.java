package com.lemnos.server.repositories;


import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.carrinho.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {
    Optional<Carrinho> findByCadastro(Cadastro cadastro);
}
