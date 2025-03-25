package com.lemnos.server.repositories.endereco;

import com.lemnos.server.models.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {
//    @Query("""
//        SELECT e FROM Endereco e
//        JOIN e.clientes c
//        WHERE c.id = :idCliente AND e.cep = :cep
//    """)
//    Optional<Endereco> findByCepAndClienteId(@Param("cep") String cep, @Param("idCliente") Integer idCliente);
//
//    @Query("""
//        SELECT e FROM Endereco e
//        JOIN e.funcionarios f
//        WHERE f.id = :idFuncionario AND e.cep = :cep
//    """)
//    Optional<Endereco> findByCepAndFuncionarioId(@Param("cep") String cep, @Param("idFuncionario") Integer idFuncionario);
}
