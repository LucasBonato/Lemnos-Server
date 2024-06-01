package com.lemnos.server.services;

import com.lemnos.server.exceptions.entidades.produto.ProdutoNotFoundException;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.carrinho.Carrinho;
import com.lemnos.server.models.carrinho.ItensCarrinho;
import com.lemnos.server.models.dtos.requests.CarrinhoRequest;
import com.lemnos.server.models.dtos.responses.CarrinhoResponse;
import com.lemnos.server.models.dtos.responses.ItemCarrinhoResponse;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.repositories.CarrinhoRepository;
import com.lemnos.server.repositories.ItensCarrinhoRepository;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.produto.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarrinhoService {
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private CarrinhoRepository carrinhoRepository;
    @Autowired private ItensCarrinhoRepository itensCarrinhoRepository;
    @Autowired private ProdutoRepository produtoRepository;

    public ResponseEntity<CarrinhoResponse> getCarrinho(String email) {
        Cadastro cadastro = cadastroRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findByCadastro(cadastro);
        if(optionalCarrinho.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        Carrinho carrinho = optionalCarrinho.get();

        List<ItemCarrinhoResponse> itens = new ArrayList<>();
        for (ItensCarrinho item : carrinho.getItens()) {
            itens.add(new ItemCarrinhoResponse(
                    item.getProduto().getId().toString(),
                    item.getQuantidade()
            ));
        }

        return ResponseEntity.ok(new CarrinhoResponse(
                carrinho.getQuantidadeProdutos(),
                carrinho.getValor(),
                itens
        ));
    }

    public ResponseEntity<Void> adicionarProduto(CarrinhoRequest carrinhoRequest) {
        Cadastro cadastro = cadastroRepository.findByEmail(carrinhoRequest.email()).orElseThrow(EntityNotFoundException::new);

        Produto produto = produtoRepository.findById(UUID.fromString(carrinhoRequest.id())).orElseThrow(ProdutoNotFoundException::new);

        Optional<Carrinho> carrinhoOptional = carrinhoRepository.findByCadastro(cadastro);
        Carrinho carrinho;
        if(carrinhoOptional.isEmpty()) {
            carrinho = new Carrinho(cadastro);
            ItensCarrinho item = new ItensCarrinho(
               carrinho,
               produto,
               carrinhoRequest.quantidade()
            );
            List<ItensCarrinho> itens = new ArrayList<>();
            itens.add(item);
            carrinho.setItens(itens);
            carrinho.setValor(getValorTotal(carrinho.getItens()));
            carrinho.setQuantidadeProdutos(getQuantidadeTotal(carrinho.getItens()));
            carrinhoRepository.save(carrinho);
            itensCarrinhoRepository.save(item);
        } else {
            carrinho = carrinhoOptional.get();
        }


        boolean contemProduto = false;

        for (ItensCarrinho item : carrinho.getItens()) {
            if (!item.getProduto().equals(produto)) continue;
            item.setQuantidade(item.getQuantidade() + 1);
            contemProduto = true;
            break;
        }
        Integer qntd = carrinhoRequest.quantidade();
        if(!contemProduto) {
            carrinho.getItens().add(new ItensCarrinho(
                    carrinho,
                    produto,
                    (qntd != null && qntd > 0) ? carrinhoRequest.quantidade() : 1
            ));
        }
        carrinho.setQuantidadeProdutos(getQuantidadeTotal(carrinho.getItens()));
        carrinho.setValor(getValorTotal(carrinho.getItens()));

        carrinhoRepository.save(carrinho);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removerProduto(CarrinhoRequest carrinhoRequest) {
        Cadastro cadastro = cadastroRepository.findByEmail(carrinhoRequest.email()).orElseThrow(EntityNotFoundException::new);
        Carrinho carrinho = carrinhoRepository.findByCadastro(cadastro).orElseThrow(() -> new RuntimeException("Carrinho já está vazio"));

        Produto produto = produtoRepository.findById(UUID.fromString(carrinhoRequest.id())).orElseThrow(ProdutoNotFoundException::new);

        for (ItensCarrinho item : carrinho.getItens()) {
            if (!item.getProduto().equals(produto)) continue;
            if (item.getQuantidade() - 1 != 0) {
                item.setQuantidade(item.getQuantidade() - 1);
                break;
            }
            carrinho.getItens().remove(item);
            break;
        }
        carrinho.setQuantidadeProdutos(getQuantidadeTotal(carrinho.getItens()));
        carrinho.setValor(getValorTotal(carrinho.getItens()));

        carrinhoRepository.save(carrinho);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removerTodosProdutos(CarrinhoRequest carrinhoRequest) {
        Cadastro cadastro = cadastroRepository.findByEmail(carrinhoRequest.email()).orElseThrow(EntityNotFoundException::new);
        Carrinho carrinho = carrinhoRepository.findByCadastro(cadastro).orElseThrow(() -> new RuntimeException("Carrinho já está vazio"));

        carrinhoRepository.delete(carrinho);
        return ResponseEntity.ok().build();
    }

    private Double getValorTotal(List<ItensCarrinho> itens) {
        Double valorTotal = 0.0;
        for(ItensCarrinho item : itens) {
            valorTotal += item.getProduto().getValor();
        }
        return valorTotal;
    }
    private Integer getQuantidadeTotal(List<ItensCarrinho> itens) {
        Integer qntdTotal = 0;
        for(ItensCarrinho item : itens) {
            qntdTotal += item.getQuantidade();
        }
        return qntdTotal;
    }
}
