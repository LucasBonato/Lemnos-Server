package com.lemnos.server.repositories.entidades;

import com.lemnos.server.models.produto.DataFornece;
import com.lemnos.server.models.produto.DataForneceId;
import com.lemnos.server.models.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataForneceRepository extends JpaRepository<DataFornece, DataForneceId> {
    DataFornece findByProduto(Produto produtoById);
}
