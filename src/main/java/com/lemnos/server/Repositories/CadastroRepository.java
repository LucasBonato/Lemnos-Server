package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Cadastro.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository<Cadastro, Integer> {
    Optional<Cadastro> findByEmail(String Email);
}
