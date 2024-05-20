package com.lemnos.server.models.dtos.responses;

import java.util.List;

public record ClienteResponse(String nome, Long Cpf, String email, String senha, List<String> produtosFavoritos,List<EnderecoResponse> enderecos) { }
