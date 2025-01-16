package com.lemnos.server.services;

import com.lemnos.server.exceptions.auth.TokenNotValidOrExpiredException;
import com.lemnos.server.exceptions.carrinho.CarrinhoVazioException;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarrinhoService {
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private CarrinhoRepository carrinhoRepository;
    @Autowired private ItensCarrinhoRepository itensCarrinhoRepository;
    @Autowired private ProdutoRepository produtoRepository;

    public ResponseEntity<CarrinhoResponse> getCarrinho(JwtAuthenticationToken token) {
        verificarToken(token);
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findByCadastro(cadastroRepository.findByEmail(token.getName()).orElseThrow(EntityNotFoundException::new));
        if(optionalCarrinho.isEmpty())
            return ResponseEntity.ok().build();

        Carrinho carrinho = optionalCarrinho.get();

        List<ItemCarrinhoResponse> items = new ArrayList<>();
        carrinho.getItens().forEach(item -> items.add(new ItemCarrinhoResponse(
                item.getProduto().getId().toString(),
                item.getQuantidade()
        )));

        return ResponseEntity.ok(new CarrinhoResponse(
                carrinho.getQuantidadeProdutos(),
                carrinho.getValor(),
                items
        ));
    }

    public ResponseEntity<Void> adicionarProduto(JwtAuthenticationToken token, CarrinhoRequest carrinhoRequest) {
        verificarToken(token);
        Cadastro cadastro = getCadastroByEmail(token.getName());
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

    public ResponseEntity<Void> removerProduto(JwtAuthenticationToken token, CarrinhoRequest carrinhoRequest) {
        verificarToken(token);
        Carrinho carrinho = getCarrinhoByCadastro(getCadastroByEmail(token.getName()));
        List<ItensCarrinho> itens = carrinho.getItens();
        Integer quantidade = (carrinhoRequest.quantidade() != null && carrinhoRequest.quantidade() > 0) ? carrinhoRequest.quantidade() : 1;
        for (ItensCarrinho item : itens) {
            if (!item.getProduto().equals(getProdutoById(carrinhoRequest.id()))) continue;
            if (item.getQuantidade() - quantidade != 0) {
                item.setQuantidade(item.getQuantidade() - quantidade);
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

    public ResponseEntity<Void> removerTodosProdutos(JwtAuthenticationToken token) {
        verificarToken(token);
        Carrinho carrinho = getCarrinhoByCadastro(getCadastroByEmail(token.getName()));
        carrinhoRepository.delete(carrinho);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Integer> quantidadeProdutos(JwtAuthenticationToken token) {
        verificarToken(token);

        Optional<Carrinho> carrinho = carrinhoRepository.findByCadastro(getCadastroByEmail(token.getName()));

        return carrinho
                .map(value -> ResponseEntity.ok(value.getItens().size()))
                .orElseGet(() -> ResponseEntity.ok(0));
    }

    private void verificarToken(JwtAuthenticationToken token) {
        if(token == null) {
            throw new TokenNotValidOrExpiredException();
        }
    }
    private Cadastro getCadastroByEmail(String email) {
        return cadastroRepository.findByEmail(email).orElseThrow(ClienteNotFoundException::new);
    }
    private Produto getProdutoById(String id) {
        return produtoRepository.findById(UUID.fromString(id)).orElseThrow(ProdutoNotFoundException::new);
    }
    private Carrinho getCarrinhoByCadastro(Cadastro cadastro) {
        return carrinhoRepository.findByCadastro(cadastro).orElseThrow(CarrinhoVazioException::new);
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
        Integer[] quantidadeTotal = {0};
        itens.forEach(item -> quantidadeTotal[0] += item.getQuantidade());
        return quantidadeTotal[0];
    }

    private Double getValorTotal(List<ItensCarrinho> itens) {
        Double[] valorTotal = {0.0};
        itens.forEach(item -> valorTotal[0] += item.getProduto().getValor() * item.getQuantidade());
        return valorTotal[0];
    }
}
