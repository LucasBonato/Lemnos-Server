package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientePossuiEnderecoRepository extends JpaRepository<ClientePossuiEndereco, ClientePossuiEnderecoId> {
    @Query(value = """
        SELECT * FROM Cliente_Possui_Endereco
        WHERE CEP = :cep
        AND Id_Cliente = :id
        """, nativeQuery = true)
    Optional<ClientePossuiEndereco> findByCepAndId_Cliente(@Param(value = "cep") String cep,@Param(value = "id") Integer id);
}
