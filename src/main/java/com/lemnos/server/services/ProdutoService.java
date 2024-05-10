package com.lemnos.server.services;

import com.lemnos.server.exceptions.entidades.produto.ProdutoNotFoundException;
import com.lemnos.server.exceptions.produto.ProdutoNotValidException;
import com.lemnos.server.models.produto.Fabricante;
import com.lemnos.server.models.produto.Produto;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.repositories.produto.ProdutoRepository;
import com.lemnos.server.repositories.produto.FabricanteRepository;
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
    @Autowired private FabricanteRepository fabricanteRepository;

    public ResponseEntity<List<ProdutoResponse>> getAll(){
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponse> dto = new ArrayList<>();

        for(Produto produto: produtos){
            dto.add(new ProdutoResponse(
                    produto.getId().toString(),
                    produto.getDescricao(),
                    produto.getCor(),
                    produto.getValor(),
                    produto.getModelo(),
                    produto.getPeso(),
                    produto.getAltura(),
                    produto.getComprimento(),
                    produto.getLargura(),
                    produto.getFabricante().getFabricante()
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<ProdutoResponse> getOneById(String id){
        System.out.println(id);
        Produto produto = getProdutoById(id);

        return ResponseEntity.ok(new ProdutoResponse(
                produto.getId().toString(),
                produto.getDescricao(),
                produto.getCor(),
                produto.getValor(),
                produto.getModelo(),
                produto.getPeso(),
                produto.getAltura(),
                produto.getComprimento(),
                produto.getLargura(),
                produto.getFabricante().getFabricante()
        ));
    }

    public ResponseEntity<Void> cadastrar(ProdutoRequest produtoRequest){
        produtoRepository.save(new Produto(produtoRequest, verificarRegraDeNegocio(produtoRequest)));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Produto getProdutoById(String id){
        return produtoRepository.findById(UUID.fromString(id)).orElseThrow(ProdutoNotFoundException::new);
    }

    private Fabricante verificarRegraDeNegocio(ProdutoRequest produtoRequest) {
        if(StringUtils.isBlank(produtoRequest.nome())){
            throw new ProdutoNotValidException(Codigo.NOME.ordinal(), "O campo Nome é obrigatório!");
        }
        if(produtoRequest.nome().length() < 5 || produtoRequest.nome().length() > 50){
            throw new ProdutoNotValidException(Codigo.NOME.ordinal(), "O nome deve conter entre 5 a 50 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.descricao())){
            throw new ProdutoNotValidException(Codigo.DESCRICAO.ordinal(), "O campo Descrição é obrigatório!");
        }
        if(produtoRequest.descricao().length() < 5 || produtoRequest.descricao().length() > 200){
            throw new ProdutoNotValidException(Codigo.DESCRICAO.ordinal(), "A descrição deve conter entre 5 e 200 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.cor())){
            throw new ProdutoNotValidException(Codigo.COR.ordinal(), "O campo Cor é obrigatório!");
        }
        if(produtoRequest.cor().length() < 4 || produtoRequest.cor().length() > 30){
            throw new ProdutoNotValidException(Codigo.COR.ordinal(), "A cor deve conter entre 4 e 30 caracteres!");
        }
        if(produtoRequest.valor() <= 0.00 || produtoRequest.valor() >= 99999999.99){
            throw new ProdutoNotValidException(Codigo.VALOR.ordinal(), "O valor deve ser entre R$0.00 e R$99999999.99");
        }
        if(StringUtils.isBlank(produtoRequest.modelo())) {
            throw new ProdutoNotValidException(Codigo.MODELO.ordinal(), "O campo Modelo é obrigatório!");
        }
        if(produtoRequest.modelo().length() < 2 || produtoRequest.modelo().length() > 30) {
            throw new ProdutoNotValidException(Codigo.MODELO.ordinal(), "O campo Modelo deve conter entre 2 e 30 caracteres!");
        }
        if(produtoRequest.peso() == null) {
            throw new ProdutoNotValidException(Codigo.PESO.ordinal(), "O campo Peso é obrigatório!");
        }
        if(produtoRequest.peso() < 0 || produtoRequest.peso() > 1000) {
            throw new ProdutoNotValidException(Codigo.PESO.ordinal(), "O campo Peso deve ser positivo e menor que 1000Kg!");
        }
        if(produtoRequest.altura() == null) {
            throw new ProdutoNotValidException(Codigo.ALTURA.ordinal(), "O campo Altura é obrigatório!");
        }
        if(produtoRequest.altura() < 0 || produtoRequest.altura() > 200) {
            throw new ProdutoNotValidException(Codigo.ALTURA.ordinal(), "O campo Altura deve ser positivo e menor que 200cm!");
        }
        if(produtoRequest.comprimento() == null) {
            throw new ProdutoNotValidException(Codigo.COMPRIMENTO.ordinal(), "O campo Comprimento é obrigatório!");
        }
        if(produtoRequest.comprimento() < 0 || produtoRequest.comprimento() > 500) {
            throw new ProdutoNotValidException(Codigo.COMPRIMENTO.ordinal(), "O campo Comprimento deve ser positivo e menor que 500cm!");
        }
        if(produtoRequest.largura() == null) {
            throw new ProdutoNotValidException(Codigo.LARGURA.ordinal(), "O campo Largura é obrigatório!");
        }
        if(produtoRequest.largura() < 0 || produtoRequest.largura() > 500) {
            throw new ProdutoNotValidException(Codigo.LARGURA.ordinal(), "O campo Largura deve ser positivo e menor que 500cm!");
        }
        if(StringUtils.isBlank(produtoRequest.fabricante())) {
            throw new ProdutoNotValidException(Codigo.FABRICANTE.ordinal(), "O campo Fabricante é obrigatório!");
        }
        if(produtoRequest.fabricante().length() < 2 || produtoRequest.fabricante().length() > 50) {
            throw new ProdutoNotValidException(Codigo.FABRICANTE.ordinal(), "O campo Fabricante deve conter entre 2 e 50 caracteres!");
        }

        return fabricanteRepository.save(new Fabricante(produtoRequest.fabricante()));
    }
}
