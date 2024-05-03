package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findByCnpj(Long cnpj);
    Optional<Fornecedor> findByEmail(String email);
}
