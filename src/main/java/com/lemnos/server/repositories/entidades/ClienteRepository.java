package com.lemnos.server.repositories.entidades;

import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCpf(Long cpf);

    Optional<Cliente> findByCadastro(Cadastro cadastro);
}
