package com.lemnos.server.models.entidades;

import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecification {
    public static Specification<Funcionario> hasNome(String nome) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome + "%");
    }

}
