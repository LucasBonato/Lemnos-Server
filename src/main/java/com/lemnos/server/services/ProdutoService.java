package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroNotValidException;
import com.lemnos.server.exceptions.entidades.produto.ProdutoNotFoundException;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.repositories.ProdutoRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired private ProdutoRepository produtoRepository;

    public ResponseEntity<List<ProdutoResponse>> getAll(){
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponse> dto = new ArrayList<>();

        for(Produto produto: produtos){
            dto.add(new ProdutoResponse(
                    produto.getId().toString(),
                    produto.getDescricao(),
                    produto.getCor(),
                    produto.getValor()
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<ProdutoResponse> getOneById(String id){
        System.out.println(id);
        Produto produto = getProdutoById(id);

        ProdutoResponse response = new ProdutoResponse(
                produto.getId().toString(),
                produto.getDescricao(),
                produto.getCor(),
                produto.getValor()
        );
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> cadastrar(ProdutoRequest produtoRequest){
        Produto produto = verificarRegraDeNegocio(produtoRequest);

        produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Produto getProdutoById(String id){
        return produtoRepository.findById(UUID.fromString(id)).orElseThrow(ProdutoNotFoundException::new);
    }

    private Produto verificarRegraDeNegocio(ProdutoRequest produtoRequest){
        if(StringUtils.isBlank(produtoRequest.nome())){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O campo Nome é obrigatório!");
        }
        if(produtoRequest.nome().length() < 5 || produtoRequest.nome().length() > 50){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O nome deve conter entre 5 a 50 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.descricao())){
            throw new CadastroNotValidException(Codigo.DESCRICAO.ordinal(), "O campo Descrição é obrigatório!");
        }
        if(produtoRequest.descricao().length() < 5 || produtoRequest.descricao().length() > 200){
            throw new CadastroNotValidException(Codigo.DESCRICAO.ordinal(), "A descrição deve conter entre 5 e 200 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.cor())){
            throw new CadastroNotValidException(Codigo.COR.ordinal(), "O campo Cor é obrigatório!");
        }
        if(produtoRequest.cor().length() < 4 || produtoRequest.cor().length() > 30){
            throw new CadastroNotValidException(Codigo.COR.ordinal(), "A cor deve conter entre 4 e 30 caracteres!");
        }
        if(produtoRequest.valor() <= 0.00 || produtoRequest.valor() >= 99999999.99){
            throw new CadastroNotValidException(Codigo.VALOR.ordinal(), "O valor deve ser entre R$0.00 e R$99999999.99");
        }
        return new Produto(produtoRequest);
    }
}
