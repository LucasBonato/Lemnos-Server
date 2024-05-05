package com.lemnos.server.models.dtos.responses;

import java.util.Date;
import java.util.List;

public record FuncionarioResponse(String nome, Long cpf, Date dataNascimento, Date dataAdmissao, Long telefone, String email, String senha, List<EnderecoResponse> enderecos) { }