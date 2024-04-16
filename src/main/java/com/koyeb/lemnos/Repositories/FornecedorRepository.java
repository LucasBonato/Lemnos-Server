package com.koyeb.lemnos.Repositories;

import com.koyeb.lemnos.Models.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    Optional<Fornecedor> findByCnpj(String cnpj);
    Optional<Fornecedor> findByEmail(String email);
}
