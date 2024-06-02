package com.lemnos.server.services;

import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
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
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findByCadastro(cadastroRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new));
        if(optionalCarrinho.isEmpty())
            return ResponseEntity.ok().build();

        Carrinho carrinho = optionalCarrinho.get();

        List<ItemCarrinhoResponse> itens = new ArrayList<>();
        carrinho.getItens().forEach(item -> itens.add(new ItemCarrinhoResponse(
                item.getProduto().getId().toString(),
                item.getQuantidade()
        )));

        return ResponseEntity.ok(new CarrinhoResponse(
                carrinho.getQuantidadeProdutos(),
                carrinho.getValor(),
                itens
        ));
    }

    public ResponseEntity<Void> adicionarProduto(CarrinhoRequest carrinhoRequest) {
        Cadastro cadastro = getCadastroByEmail(carrinhoRequest.email());
        Produto produto = getProdutoById(carrinhoRequest.id());

        Carrinho carrinho = carrinhoRepository.findByCadastro(cadastro).orElse(null);

        if(carrinho == null) {
            carrinho = criarNovoCarrinho(cadastro, produto, carrinhoRequest.quantidade());
        } else {
            atualizarCarrinho(carrinho, produto, carrinhoRequest.quantidade());
        }

        carrinhoRepository.save(carrinho);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removerProduto(CarrinhoRequest carrinhoRequest) {
        Carrinho carrinho = getCarrinhoByCadastro(getCadastroByEmail(carrinhoRequest.email()));
        List<ItensCarrinho> itens = carrinho.getItens();
        for (ItensCarrinho item : itens) {
            if (!item.getProduto().equals(getProdutoById(carrinhoRequest.id()))) continue;
            if (item.getQuantidade() - 1 != 0) {
                item.setQuantidade(item.getQuantidade() - 1);
                break;
            }
            itens.remove(item);
            break;
        }

        if(itens.isEmpty()) {
            carrinhoRepository.delete(carrinho);
        } else {
            carrinho.setAll(itens, getValorTotal(itens), getQuantidadeTotal(itens));
            carrinhoRepository.save(carrinho);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removerTodosProdutos(CarrinhoRequest carrinhoRequest) {
        Carrinho carrinho = getCarrinhoByCadastro(getCadastroByEmail(carrinhoRequest.email()));
        carrinhoRepository.delete(carrinho);
        return ResponseEntity.ok().build();
    }

    private Cadastro getCadastroByEmail(String email) {
        return cadastroRepository.findByEmail(email).orElseThrow(ClienteNotFoundException::new);
    }
    private Produto getProdutoById(String id) {
        return produtoRepository.findById(UUID.fromString(id)).orElseThrow(ProdutoNotFoundException::new);
    }
    private Carrinho getCarrinhoByCadastro(Cadastro cadastro) {
        return carrinhoRepository.findByCadastro(cadastro).orElseThrow(() -> new RuntimeException("Carrinho já está vazio"));
    }
    private Carrinho criarNovoCarrinho(Cadastro cadastro, Produto produto, Integer quantidade) {
        Carrinho carrinho = new Carrinho(cadastro);
        List<ItensCarrinho> itens = new ArrayList<>();
        ItensCarrinho item = new ItensCarrinho(carrinho, produto, quantidade);
        itens.add(item);
        carrinho.setAll(itens, getValorTotal(itens), getQuantidadeTotal(itens));
        carrinhoRepository.save(carrinho);
        itensCarrinhoRepository.save(item);
        return carrinho;
    }
    private void atualizarCarrinho(Carrinho carrinho, Produto produto, Integer quantidade) {
        List<ItensCarrinho> itens = carrinho.getItens();
        boolean contemProduto = itens.stream()
                .filter(item -> item.getProduto().equals(produto))
                .peek(item -> item.setQuantidade(item.getQuantidade() + (quantidade != null && quantidade > 0 ? quantidade : 1)))
                .findFirst()
                .isPresent();

        if (!contemProduto) itens.add(new ItensCarrinho(carrinho, produto, (quantidade != null && quantidade > 0) ? quantidade : 1));

        carrinho.setAll(itens, getValorTotal(itens), getQuantidadeTotal(itens));
    }

    private Integer getQuantidadeTotal(List<ItensCarrinho> itens) {
        Integer[] qntdTotal = {0};
        itens.forEach(item -> qntdTotal[0] += item.getQuantidade());
        return qntdTotal[0];
    }

    private Double getValorTotal(List<ItensCarrinho> itens) {
        Double[] valorTotal = {0.0};
        itens.forEach(item -> valorTotal[0] += item.getProduto().getValor() * item.getQuantidade());
        return valorTotal[0];
    }
}
