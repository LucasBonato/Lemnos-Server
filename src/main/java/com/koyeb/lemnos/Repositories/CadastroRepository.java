package com.koyeb.lemnos.Repositories;

import com.koyeb.lemnos.Models.Cadastro.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository<Cadastro, Integer> {
    Optional<Cadastro> findByEmail(String Email);
}
