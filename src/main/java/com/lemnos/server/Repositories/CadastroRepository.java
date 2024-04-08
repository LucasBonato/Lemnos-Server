package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Integer> {
}
