package com.lemnos.server.repositories.entidades;

import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.produto.DataFornece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findByCnpj(Long cnpj);
    Optional<Fornecedor> findByEmail(String email);
    Optional<Fornecedor> findByNome(String fornecedor);

    List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
}
