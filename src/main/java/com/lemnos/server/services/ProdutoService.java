package com.lemnos.server.services;

import com.lemnos.server.exceptions.entidades.cliente.ClienteNotFoundException;
import com.lemnos.server.exceptions.entidades.fornecedor.FornecedorNotFoundException;
import com.lemnos.server.exceptions.entidades.produto.ProdutoNotFoundException;
import com.lemnos.server.exceptions.produto.AvaliacaoNotValidException;
import com.lemnos.server.exceptions.produto.ProdutoAlreadyFavoritoException;
import com.lemnos.server.exceptions.produto.ProdutoNotValidException;
import com.lemnos.server.models.dtos.requests.ProdutoFiltroRequest;
import com.lemnos.server.models.dtos.responses.FavoritoResponse;
import com.lemnos.server.models.entidades.Cliente;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.produto.*;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.produto.categoria.SubCategoria;
import com.lemnos.server.models.produto.imagens.Imagem;
import com.lemnos.server.models.produto.imagens.ImagemPrincipal;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.ClienteRepository;
import com.lemnos.server.repositories.entidades.DataForneceRepository;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.repositories.produto.AvaliacaoRepository;
import com.lemnos.server.repositories.produto.DescontoRepository;
import com.lemnos.server.repositories.produto.ProdutoRepository;
import com.lemnos.server.repositories.entidades.FabricanteRepository;
import com.lemnos.server.repositories.produto.SubCategoriaRepository;
import com.lemnos.server.repositories.produto.imagens.ImagemPrincipalRepository;
import com.lemnos.server.repositories.produto.imagens.ImagemRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProdutoService {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private CadastroRepository cadastroRepository;
    @Autowired private AvaliacaoRepository avaliacaoRepository;
    @Autowired private FabricanteRepository fabricanteRepository;
    @Autowired private DataForneceRepository dataForneceRepository;
    @Autowired private FornecedorRepository fornecedorRepository;
    @Autowired private SubCategoriaRepository subCategoriaRepository;
    @Autowired private ImagemPrincipalRepository imagemPrincipalRepository;
    @Autowired private ImagemRepository imagemRepository;
    @Autowired private DescontoRepository descontoRepository;

    @Cacheable("allProdutos")
    public ResponseEntity<List<ProdutoResponse>> getAll(){
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponse> dto = new ArrayList<>();

        for(Produto produto : produtos){
            dto.add(getProdutoResponse(produto));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<ProdutoResponse> getOneById(String id){
        return ResponseEntity.ok(getProdutoResponse(getProdutoById(id)));
    }

    public ResponseEntity<List<ProdutoResponse>> getBy(ProdutoFiltroRequest filtro) {
        Specification<Produto> specification = Specification.where(null);

        if(StringUtils.isNotBlank(filtro.nome())) {
            specification = specification.and(ProdutoSpecifications.hasNome(filtro.nome()));
        }
        if (StringUtils.isNotBlank(filtro.categoria())) {
            specification = specification.and(ProdutoSpecifications.hasCategoria(filtro.categoria()));
        }
        if (StringUtils.isNotBlank(filtro.subCategoria())) {
            specification = specification.and(ProdutoSpecifications.hasSubCategoria(filtro.subCategoria()));
        }
        if (StringUtils.isNotBlank(filtro.marca())) {
            specification = specification.and(ProdutoSpecifications.hasFabricante(filtro.marca()));
        }
        if ((filtro.menorPreco() != null && filtro.menorPreco() >= 0) && (filtro.maiorPreco() != null && filtro.maiorPreco() >= 0)) {
            specification = specification.and(ProdutoSpecifications.isPrecoBetween(filtro.menorPreco(), filtro.maiorPreco()));
        } else if (filtro.menorPreco() == null && (filtro.maiorPreco() != null && filtro.maiorPreco() >= 0)) {
            specification = specification.and(ProdutoSpecifications.isPrecoBetween(0.0, filtro.maiorPreco()));
        }
        int page = (filtro.page() != null && filtro.page() > 0) ? filtro.page() : 0;
        int size = (filtro.size() != null && filtro.size() > 0) ? filtro.size() : 10;
        Pageable pageble = PageRequest.of(page, size);

        List<ProdutoResponse> produtoResponses = new ArrayList<>();
        produtoRepository.findAll(specification, pageble)
                .forEach(produto -> produtoResponses.add(getProdutoResponse(produto)));

        return ResponseEntity.ok(produtoResponses);
    }

    public ResponseEntity<Void> cadastrar(ProdutoRequest produtoRequest){
        verificarRegraDeNegocioCreate(produtoRequest);

        Produto produto = produtoRepository.save(new Produto(
            produtoRequest,
            getFabricante(produtoRequest.fabricante()),
            getSubCategoria(produtoRequest.subCategoria()),
            getImagemPrincipal(produtoRequest),
            getDesconto(produtoRequest.desconto())
        ));

        Fornecedor fornecedor = fornecedorRepository.findByNome(produtoRequest.fornecedor()).orElseThrow(FornecedorNotFoundException::new);
        dataForneceRepository.save(new DataFornece(fornecedor, produto));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> update(String id, ProdutoRequest produtoRequest) {
        Produto produto = getProdutoById(id);

        Fabricante fabricante = (StringUtils.isBlank(produtoRequest.fabricante())) ? produto.getFabricante() : getFabricante(produtoRequest.fabricante());
        SubCategoria subCategoria = (StringUtils.isBlank(produtoRequest.subCategoria())) ? produto.getSubCategoria() : getSubCategoria(produtoRequest.subCategoria());
        ImagemPrincipal imagemPrincipal = produto.getImagemPrincipal();
        Desconto desconto = (StringUtils.isBlank(produtoRequest.desconto())) ? produto.getDesconto() : getDesconto(produtoRequest.desconto());

        produto.setAll(produtoRequest, fabricante, subCategoria, imagemPrincipal, desconto);
        verificarRegraDeNegocioUpdate(produto);

        produtoRepository.save(produto);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> delete(String id){
        produtoRepository.delete(getProdutoById(id));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<FavoritoResponse>> getFavoritos(String email) {
        Cliente cliente = getClienteByEmail(email);
        List<FavoritoResponse> response = new ArrayList<>();
        cliente.getProdutosFavoritos().forEach(produto -> response.add(new FavoritoResponse(produto.getId().toString())));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> favoritar(String email, String idProd) {
        Cliente cliente = getClienteByEmail(email);
        Produto produto = getProdutoById(idProd);

        if(cliente.getProdutosFavoritos().remove(produto)) throw new ProdutoAlreadyFavoritoException("O produto já está favoritado");

        cliente.getProdutosFavoritos().add(produto);
        clienteRepository.save(cliente);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> desfavoritar(String email, String idProd) {
        Cliente cliente = getClienteByEmail(email);
        List<Produto> novosProdutosFavoritos = new ArrayList<>();
        cliente.getProdutosFavoritos()
                .stream()
                .filter(produto -> !produto.getId().toString().equals(idProd))
                .forEach(novosProdutosFavoritos::add);
        cliente.setProdutosFavoritos(novosProdutosFavoritos);
        clienteRepository.save(cliente);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> retirarPorcentagem(String idProduto){
        Produto produto = getProdutoById(idProduto);
        produto.setDesconto(getDesconto(null));
        produtoRepository.save(produto);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> avaliar(String idProduto, Double valorAvaliacao) {
        if(valorAvaliacao < 1.0 || valorAvaliacao > 5.0) throw new AvaliacaoNotValidException("A avaliação precisa estar entre 1.0 e 5.0");
        avaliacaoRepository.save(new Avaliacao(getProdutoById(idProduto), arredondarValor(valorAvaliacao)));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void verificarRegraDeNegocioCreate(ProdutoRequest produtoRequest) {
        if(StringUtils.isBlank(produtoRequest.nome())){
            throw new ProdutoNotValidException(Codigo.NOME, "O campo Nome é obrigatório!");
        }
        if(produtoRequest.nome().length() < 5 || produtoRequest.nome().length() > 100){
            throw new ProdutoNotValidException(Codigo.NOME, "O nome deve conter entre 5 a 100 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.descricao())){
            throw new ProdutoNotValidException(Codigo.DESCRICAO, "O campo Descrição é obrigatório!");
        }
        if(produtoRequest.descricao().length() < 5 || produtoRequest.descricao().length() > 1024){
            throw new ProdutoNotValidException(Codigo.DESCRICAO, "A descrição deve conter entre 5 e 1024 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.cor())){
            throw new ProdutoNotValidException(Codigo.COR, "O campo Cor é obrigatório!");
        }
        if(produtoRequest.cor().length() < 2 || produtoRequest.cor().length() > 30){
            throw new ProdutoNotValidException(Codigo.COR, "A cor deve conter entre 2 e 30 caracteres!");
        }
        if(produtoRequest.valor() < 0.00 || produtoRequest.valor() > 99999999.99){
            throw new ProdutoNotValidException(Codigo.VALOR, "O valor deve ser entre R$0.00 e R$99999999.99");
        }
        if(StringUtils.isBlank(produtoRequest.modelo())) {
            throw new ProdutoNotValidException(Codigo.MODELO, "O campo Modelo é obrigatório!");
        }
        if(produtoRequest.modelo().length() < 2 || produtoRequest.modelo().length() > 30) {
            throw new ProdutoNotValidException(Codigo.MODELO, "O campo Modelo deve conter entre 2 e 30 caracteres!");
        }
        if(produtoRequest.peso() == null) {
            throw new ProdutoNotValidException(Codigo.PESO, "O campo Peso é obrigatório!");
        }
        if(produtoRequest.peso() < 0 || produtoRequest.peso() > 1000) {
            throw new ProdutoNotValidException(Codigo.PESO, "O campo Peso deve ser positivo e menor que 1000Kg!");
        }
        if(produtoRequest.altura() == null) {
            throw new ProdutoNotValidException(Codigo.ALTURA, "O campo Altura é obrigatório!");
        }
        if(produtoRequest.altura() < 0 || produtoRequest.altura() > 500) {
            throw new ProdutoNotValidException(Codigo.ALTURA, "O campo Altura deve ser positivo e menor que 500cm!");
        }
        if(produtoRequest.comprimento() == null) {
            throw new ProdutoNotValidException(Codigo.COMPRIMENTO, "O campo Comprimento é obrigatório!");
        }
        if(produtoRequest.comprimento() < 0 || produtoRequest.comprimento() > 500) {
            throw new ProdutoNotValidException(Codigo.COMPRIMENTO, "O campo Comprimento deve ser positivo e menor que 500cm!");
        }
        if(produtoRequest.largura() == null) {
            throw new ProdutoNotValidException(Codigo.LARGURA, "O campo Largura é obrigatório!");
        }
        if(produtoRequest.largura() < 0 || produtoRequest.largura() > 500) {
            throw new ProdutoNotValidException(Codigo.LARGURA, "O campo Largura deve ser positivo e menor que 500cm!");
        }
        if(StringUtils.isBlank(produtoRequest.fabricante())) {
            throw new ProdutoNotValidException(Codigo.FABRICANTE, "O campo Fabricante é obrigatório!");
        }
        if(produtoRequest.fabricante().length() < 2 || produtoRequest.fabricante().length() > 50) {
            throw new ProdutoNotValidException(Codigo.FABRICANTE, "O campo Fabricante deve conter entre 2 e 50 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.fornecedor())) {
            throw new ProdutoNotValidException(Codigo.GLOBAL, "O campo Fornecedor é obrigatório!");
        }
        if(StringUtils.isBlank(produtoRequest.subCategoria())){
            throw new ProdutoNotValidException(Codigo.SUBCATEGORIA, "O campo Subcategoria é obrigatório!");
        }
        if(produtoRequest.subCategoria().length() < 2 || produtoRequest.subCategoria().length() > 30) {
            throw new ProdutoNotValidException(Codigo.FABRICANTE, "O campo Subcategoria deve conter entre 2 e 30 caracteres!");
        }
        if(StringUtils.isBlank(produtoRequest.imagemPrincipal())){
            throw new ProdutoNotValidException(Codigo.IMGPRINCIPAL, "O campo Imagem Principal é obrigatório!");
        }
        if(produtoRequest.imagens() == null) {
            throw new ProdutoNotValidException(Codigo.IMAGENS, "É necessário ter no minimo uma imagem secundária!");
        }
    }
    private void verificarRegraDeNegocioUpdate(Produto produto) {
        if(produto.getNomeProduto().length() < 5 || produto.getNomeProduto().length() > 100){
            throw new ProdutoNotValidException(Codigo.NOME, "O nome deve conter entre 5 a 100 caracteres!");
        }
        if(produto.getDescricao().length() < 5 || produto.getDescricao().length() > 1024){
            throw new ProdutoNotValidException(Codigo.DESCRICAO, "A descrição deve conter entre 5 e 1024 caracteres!");
        }
        if(produto.getCor().length() < 2 || produto.getCor().length() > 30){
            throw new ProdutoNotValidException(Codigo.COR, "A cor deve conter entre 2 e 30 caracteres!");
        }
        if(produto.getValor() < 0.00 || produto.getValor() > 99999999.99){
            throw new ProdutoNotValidException(Codigo.VALOR, "O valor deve ser entre R$0.00 e R$99999999.99");
        }
        if(produto.getModelo().length() < 2 || produto.getModelo().length() > 30) {
            throw new ProdutoNotValidException(Codigo.MODELO, "O campo Modelo deve conter entre 2 e 30 caracteres!");
        }
        if(produto.getPeso() < 0 || produto.getPeso() > 1000) {
            throw new ProdutoNotValidException(Codigo.PESO, "O campo Peso deve ser positivo e menor que 1000Kg!");
        }
        if(produto.getAltura() < 0 || produto.getAltura() > 500) {
            throw new ProdutoNotValidException(Codigo.ALTURA, "O campo Altura deve ser positivo e menor que 500cm!");
        }
        if(produto.getComprimento() < 0 || produto.getComprimento() > 500) {
            throw new ProdutoNotValidException(Codigo.COMPRIMENTO, "O campo Comprimento deve ser positivo e menor que 500cm!");
        }
        if(produto.getLargura() < 0 || produto.getLargura() > 500) {
            throw new ProdutoNotValidException(Codigo.LARGURA, "O campo Largura deve ser positivo e menor que 500cm!");
        }
        if(produto.getFabricante().getFabricante().length() < 2 || produto.getFabricante().getFabricante().length() > 50) {
            throw new ProdutoNotValidException(Codigo.FABRICANTE, "O campo Fabricante deve conter entre 2 e 50 caracteres!");
        }
        if(produto.getSubCategoria().getSubCategoria().length() < 2 || produto.getSubCategoria().getSubCategoria().length() > 30) {
            throw new ProdutoNotValidException(Codigo.SUBCATEGORIA, "O campo Subcategoria deve conter entre 2 e 30 caracteres!");
        }
        if(StringUtils.isBlank(produto.getImagemPrincipal().getImagemPrincipal())){
            throw new ProdutoNotValidException(Codigo.IMGPRINCIPAL, "O campo Imagem Principal é obrigatório!");
        };
        if(StringUtils.isBlank(produto.getImagemPrincipal().getImagens().toString())){
            throw new ProdutoNotValidException(Codigo.IMAGENS, "O campo Imagens é obrigatório!");
        }
    }

    private Cliente getClienteByEmail(String email) {
        return clienteRepository.findByCadastro(cadastroRepository.findByEmail(email.replace("%40", "@")).orElseThrow(ClienteNotFoundException::new)).orElseThrow(ClienteNotFoundException::new);
    }
    private Produto getProdutoById(String id){
        return produtoRepository.findById(UUID.fromString(id)).orElseThrow(ProdutoNotFoundException::new);
    }
    private ProdutoResponse getProdutoResponse(Produto produto) {
        List<String> imagens = new ArrayList<>();
        for (Imagem imagem : produto.getImagemPrincipal().getImagens()) {
            imagens.add(imagem.getImagem());
        }
        return new ProdutoResponse(
                produto.getId().toString(),
                produto.getNomeProduto(),
                produto.getDescricao(),
                produto.getCor(),
                produto.getValor(),
                calcularPorcentagem(produto),
                produto.getModelo(),
                produto.getPeso(),
                produto.getAltura(),
                produto.getComprimento(),
                produto.getLargura(),
                produto.getFabricante().getFabricante(),
                produto.getSubCategoria().getCategoria().getNome(),
                produto.getSubCategoria().getSubCategoria(),
                produto.getImagemPrincipal().getImagemPrincipal(),
                imagens,
                produto.getDesconto().getValorDesconto(),
                calcularAvaliacao(produto),
                avaliacaoRepository.findAllByProduto(produto).size()
        );
    }

    private Double calcularPorcentagem(Produto produto){
        Double porcentagem = produto.getValor() * (Double.parseDouble(produto.getDesconto().getValorDesconto()) / 100);
        return produto.getValor() - porcentagem;
    }
    private Double calcularAvaliacao(Produto produto) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByProduto(produto);
        Double media = 0.0;
        for(Avaliacao avaliacao : avaliacoes) media += avaliacao.getAvaliacao();
        media /= avaliacoes.size();
        return arredondarValor(media);
    }
    private Double arredondarValor(Double valor) { return Math.round(valor * 2) / 2.0; }

    private Fabricante getFabricante(String fabricante) {
        Optional<Fabricante> fabricanteOptional = fabricanteRepository.findByFabricante(fabricante);
        return fabricanteOptional.orElseGet(() -> fabricanteRepository.save(new Fabricante(fabricante)));
    }
    private SubCategoria getSubCategoria(String subCategoria) {
        return subCategoriaRepository
                .findBySubCategoria(subCategoria)
                .orElseThrow(() -> new ProdutoNotValidException(Codigo.SUBCATEGORIA, "SubCategoria inexistente!"));
    }
    private ImagemPrincipal getImagemPrincipal(ProdutoRequest produtoRequest) {
        List<Imagem> imagens = new ArrayList<>();
        ImagemPrincipal imagemPrincipal = new ImagemPrincipal(produtoRequest.imagemPrincipal());
        imagemPrincipalRepository.save(imagemPrincipal);
        produtoRequest.imagens().forEach(imagem -> imagens.add(imagemRepository.save(new Imagem(imagem, imagemPrincipal))));
        imagemPrincipal.setImagens(imagens);
        return imagemPrincipalRepository.save(imagemPrincipal);
    }
    private Desconto getDesconto(String desconto){
        Optional<Desconto> descontoOptional = descontoRepository.findByValorDesconto((desconto == null) ? "0" : desconto);
        if(descontoOptional.isPresent()) return descontoOptional.get();
        throw new ProdutoNotValidException(Codigo.DESCONTO, "Desconto inválido, digite um número entre 0 e 99!");
    }
}