package com.lemnos.server.repositories.cadastro;

import com.lemnos.server.models.cadastro.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Integer> {
    Optional<Cadastro> findByEmail(String Email);
}
