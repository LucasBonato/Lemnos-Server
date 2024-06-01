package com.lemnos.server.repositories.entidades;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.entidades.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Optional<Fornecedor> findByCnpj(Long cnpj);
    Optional<Fornecedor> findByEmail(String email);

    Optional<Fornecedor> findByNome(String fornecedor);

    Optional<Fornecedor> findByCadastro(Cadastro cadastro);
}
