package com.lemnos.server.Repositories;

import com.lemnos.server.Models.Endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.Models.Endereco.Possui.FuncionarioPossuiEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioPossuiEnderecoRepository extends JpaRepository<FuncionarioPossuiEndereco, FuncionarioPossuiEnderecoId> { }
