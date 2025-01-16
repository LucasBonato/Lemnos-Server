package com.lemnos.server.repositories.endereco.possui;

import com.lemnos.server.models.endereco.possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.endereco.possui.FuncionarioPossuiEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioPossuiEnderecoRepository extends JpaRepository<FuncionarioPossuiEndereco, FuncionarioPossuiEnderecoId> {
    @Query(value = """
        SELECT * FROM Funcionario_Possui_Endereco
        WHERE CEP = :cep
        AND Id_Funcionario = :id
        """, nativeQuery = true)
    Optional<FuncionarioPossuiEndereco> findByCepAndId_Cliente(@Param(value = "cep") String cep, @Param(value = "id") Integer id);
}
