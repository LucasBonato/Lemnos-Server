package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    Optional<Fornecedor> findByCnpj(Long cnpj);
    Optional<Fornecedor> findByEmail(String email);
}
