package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEndereco;
import com.lemnos.server.Models.Endereco.Possui.ClientePossuiEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientePossuiEnderecoRepository extends JpaRepository<ClientePossuiEndereco, ClientePossuiEnderecoId> { }
