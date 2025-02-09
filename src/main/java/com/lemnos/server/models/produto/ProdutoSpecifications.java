package com.lemnos.server.models.produto;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecifications {
    public static Specification<Produto> hasNome(String nome) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Produto> hasCategoria(String categoria) {
        return (root, query, cb) -> cb.equal(root.join("subCategoria", JoinType.INNER).join("categoria", JoinType.INNER).get("nome"), categoria);
    }

    public static Specification<Produto> hasSubCategoria(String subCategoria) {
        return (root, query, cb) -> cb.equal(root.join("subCategoria", JoinType.INNER).get("subCategoria"), subCategoria);
    }

    public static Specification<Produto> hasFabricante(String fabricante) {
        return (root, query, cb) -> cb.equal(root.join("fabricante", JoinType.INNER).get("fabricante"), fabricante);
    }

    public static Specification<Produto> isPrecoBetween(Double menorPreco, Double maiorPreco) {
        return (root, query, cb) -> cb.between(root.get("valor"), menorPreco, maiorPreco);
    }

    public static Specification<Produto> hasAvaliacao(Double avaliacao){
        return (root, query, cb) -> cb.equal(root.get("mediaAvaliacao"), avaliacao);
    }

    public static Specification<Produto> hasDescricao(String descricao) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
    }

    public static Specification<Produto> hasNomeOrDescricao(String filtro) {
        return (root, query, cb) -> cb.or(
                hasNome(filtro).toPredicate(root, query, cb),
                hasDescricao(filtro).toPredicate(root, query, cb)
        );
    }
}
