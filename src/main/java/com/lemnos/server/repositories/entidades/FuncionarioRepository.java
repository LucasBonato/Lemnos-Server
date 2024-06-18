package com.lemnos.server.repositories.entidades;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.entidades.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> {
    Optional<Funcionario> findByCpf(Long cpf);

    Optional<Funcionario> findByCadastro(Cadastro cadastro);

    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
}
