package com.lemnos.server.services;

import com.lemnos.server.exceptions.auth.TokenNotValidOrExpiredException;
import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.entidades.produto.ProdutoNotFoundException;
import com.lemnos.server.exceptions.produto.ProdutoAlreadyFavoritoException;
import com.lemnos.server.models.dtos.responses.FavoritoResponse;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FavoritoService {
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private ProdutoRepository produtoRepository;

    public ResponseEntity<List<FavoritoResponse>> getFavoritos(JwtAuthenticationToken token) {
        verifyToken(token);
        Cliente cliente = getClienteByEmail(token.getName());
        List<FavoritoResponse> response = new ArrayList<>();
        cliente.getProdutosFavoritos().forEach(produto -> response.add(new FavoritoResponse(produto.getId().toString())));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> favoritar(JwtAuthenticationToken token, String idProd) {
        verifyToken(token);
        Cliente cliente = getClienteByEmail(token.getName());
        Produto produto = getProdutoById(idProd);

        if(cliente.getProdutosFavoritos().remove(produto)) throw new ProdutoAlreadyFavoritoException("O produto já está favoritado");

        cliente.getProdutosFavoritos().add(produto);
        clienteRepository.save(cliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> desfavoritar(JwtAuthenticationToken token, String idProd) {
        verifyToken(token);
        Cliente cliente = getClienteByEmail(token.getName());
        List<Produto> novosProdutosFavoritos = new ArrayList<>();
        cliente.getProdutosFavoritos()
                .stream()
                .filter(produto -> !produto.getId().toString().equals(idProd))
                .forEach(novosProdutosFavoritos::add);
        cliente.setProdutosFavoritos(novosProdutosFavoritos);
        clienteRepository.save(cliente);
        return ResponseEntity.ok().build();
    }

    private void verifyToken(JwtAuthenticationToken token) {
        if(token == null) {
            throw new TokenNotValidOrExpiredException();
        }
    }
    private Cliente getClienteByEmail(String email) {
        return clienteRepository.findByCadastro(
            cadastroRepository.findByEmail(email.replace("%40", "@")
        ).orElseThrow(ClienteNotFoundException::new)).orElseThrow(ClienteNotFoundException::new);
    }
    private Produto getProdutoById(String id){
        return produtoRepository.findById(UUID.fromString(id)).orElseThrow(ProdutoNotFoundException::new);
    }
}
