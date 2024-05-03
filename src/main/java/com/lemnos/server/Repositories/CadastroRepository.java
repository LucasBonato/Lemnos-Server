package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Cadastro.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Integer> {
    Optional<Cadastro> findByEmail(String Email);
}
