package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroCnpjAlreadyInUseException;
import com.lemnos.server.exceptions.cadastro.CadastroNotValidException;
import com.lemnos.server.exceptions.entidades.fornecedor.FornecedorNotFoundException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.models.dtos.responses.IdResponse;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.entidades.Fornecedor;
import com.lemnos.server.models.dtos.responses.FornecedorResponse;
import com.lemnos.server.repositories.entidades.FornecedorRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService extends Util {

    @Autowired private FornecedorRepository fornecedorRepository;

    @Cacheable("allFornecedores")
    public ResponseEntity<List<FornecedorResponse>> getAll() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        List<FornecedorResponse> dto = new ArrayList<>();
        for(Fornecedor fornecedor : fornecedores){
            dto.add(new FornecedorResponse(
                    fornecedor.getNome(),
                    fornecedor.getCnpj(),
                    fornecedor.getTelefone(),
                    fornecedor.getEmail(),
                    getEnderecoRecords(fornecedor)
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<FornecedorResponse> getOneByEmail(String email) {
        Fornecedor fornecedor = getOneFornecedorByEmail(email);
        FornecedorResponse record = new FornecedorResponse(
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                getEnderecoRecords(fornecedor)
        );
        return ResponseEntity.ok(record);
    }

    public ResponseEntity<IdResponse> getByEmail(String email) {
        Fornecedor fornecedor = fornecedorRepository.findByEmail(email).orElseThrow(FornecedorNotFoundException::new);
        return ResponseEntity.ok(new IdResponse(fornecedor.getId()));
    }

    public ResponseEntity<Void> updateFornecedor(String email, FornecedorRequest fornecedorRequest) {
        Fornecedor updatedFornecedor = insertData(email, fornecedorRequest);
        fornecedorRepository.save(updatedFornecedor);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteByEmail(String email) {
        Fornecedor fornecedorDeletado = getOneFornecedorByEmail(email);

        if(fornecedorDeletado.getSituacao() == Situacao.ATIVO) {
            fornecedorDeletado.setSituacao(Situacao.INATIVO);
            fornecedorRepository.save(fornecedorDeletado);
        }
        return ResponseEntity.ok().build();
    }

    private Fornecedor getOneFornecedorByEmail(String email) {
        return fornecedorRepository.findByEmail(email).orElseThrow(FornecedorNotFoundException::new);
    }
    private static EnderecoResponse getEnderecoRecords(Fornecedor fornecedor) {
        if(fornecedor.getEndereco() == null) return null;
        return new EnderecoResponse(
                fornecedor.getEndereco().getCep(),
                fornecedor.getEndereco().getLogradouro(),
                fornecedor.getNumeroLogradouro(),
                fornecedor.getComplemento(),
                fornecedor.getEndereco().getCidade().getCidade(),
                fornecedor.getEndereco().getBairro(),
                fornecedor.getEndereco().getEstado().getUf()
        );
    }
    private Fornecedor insertData(String email, FornecedorRequest fornecedorEnviado){
        Fornecedor fornecedorEncontrado = getOneFornecedorByEmail(email);

        if(StringUtils.isBlank(fornecedorEnviado.nome()) && StringUtils.isBlank(fornecedorEnviado.telefone()) && StringUtils.isBlank(fornecedorEnviado.cnpj())){
            throw new UpdateNotValidException("Fornecedor");
        }
        if(StringUtils.isBlank(fornecedorEnviado.nome())){
            fornecedorEnviado = fornecedorEnviado.setNome(fornecedorEncontrado.getNome());
        }
        if(fornecedorEnviado.nome().length() < 2 || fornecedorEnviado.nome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME, "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(fornecedorEnviado.telefone() != null && fornecedorEnviado.telefone().length() != 11){
            throw new CadastroNotValidException(Codigo.TELEFONE, "Telefone inválido! (XX)XXXXX-XXXX");
        }
        if(fornecedorEnviado.telefone() == null || fornecedorEnviado.telefone().isBlank()){
            fornecedorEnviado = fornecedorEnviado.setTelefone(fornecedorEncontrado.getTelefone().toString());
        }
        Long telefone = convertStringToLong(fornecedorEnviado.telefone(), Codigo.TELEFONE);
        if(fornecedorEnviado.cnpj() == null){
            fornecedorEnviado = fornecedorEnviado.setCnpj(fornecedorEncontrado.getCnpj().toString());
        }
        Long cpnj = Long.parseLong(fornecedorEnviado.cnpj());

        Optional<Fornecedor> cnpj = fornecedorRepository.findByCnpj(cpnj);
        if(cnpj.isPresent() && !Objects.equals(cnpj.get().getId(), fornecedorEncontrado.getId())) throw new CadastroCnpjAlreadyInUseException();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(fornecedorEncontrado.getId());
        fornecedor.setNome(fornecedorEnviado.nome());
        fornecedor.setTelefone(telefone);
        fornecedor.setCnpj(cpnj);
        fornecedor.setEmail(fornecedorEncontrado.getEmail());
        fornecedor.setNumeroLogradouro(fornecedorEncontrado.getNumeroLogradouro());
        fornecedor.setEndereco(fornecedorEncontrado.getEndereco());

        return fornecedor;
    }
}