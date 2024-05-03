package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCnpjAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroNotValidException;
import com.lemnos.server.Exceptions.Endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.DTOs.EnderecoDTO;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.Endereco.Endereco;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Enums.Situacao;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Models.Records.FornecedorRecord;
import com.lemnos.server.Repositories.FornecedorRepository;
import com.lemnos.server.Utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService extends Util {

    @Autowired private FornecedorRepository fornecedorRepository;

    public ResponseEntity<List<FornecedorRecord>> getAll() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        List<FornecedorRecord> dto = new ArrayList<>();
        for(Fornecedor fornecedor : fornecedores){
            dto.add(new FornecedorRecord(
                    fornecedor.getNome(),
                    fornecedor.getCnpj(),
                    fornecedor.getTelefone(),
                    fornecedor.getEmail(),
                    getEnderecoRecords(fornecedor)
            ));
        }

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<FornecedorRecord> getOneById(Integer id) {
        Fornecedor fornecedor = getOneFornecedorById(id);
        FornecedorRecord record = new FornecedorRecord(
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                getEnderecoRecords(fornecedor)
        );
        return ResponseEntity.ok(record);
    }

    public ResponseEntity<Void> createEndereco(Integer id, EnderecoDTO enderecoDTO) {
        Fornecedor fornecedor = getOneFornecedorById(id);

        Endereco endereco = getEndereco(enderecoDTO);

        if(fornecedor.getEndereco() != null) throw new EntityAlreadyHasEnderecoException("Fornecedor");

        fornecedor.setEndereco(endereco);
        fornecedorRepository.save(fornecedor);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> updateFornecedor(Integer id, FornecedorDTO fornecedorDTO) {
        Fornecedor updatedFornecedor = insertData(id, fornecedorDTO);
        fornecedorRepository.save(updatedFornecedor);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteById(Integer id) {
        Fornecedor fornecedorDeletado = getOneFornecedorById(id);

        if(fornecedorDeletado.getSituacao() == Situacao.ATIVO) {
            fornecedorDeletado.setSituacao(Situacao.INATIVO);
            fornecedorRepository.save(fornecedorDeletado);
        }
        return ResponseEntity.ok().build();
    }

    private Fornecedor insertData(Integer id, FornecedorDTO fornecedorEnviado){
        Fornecedor fornecedorEncontrado = getOneFornecedorById(id);

        if(StringUtils.isBlank(fornecedorEnviado.getNome()) && (fornecedorEnviado.getTelefone() == null || fornecedorEnviado.getTelefone().isBlank()) && (fornecedorEnviado.getCnpj() == null || fornecedorEnviado.getCnpj().isBlank())){
            throw new UpdateNotValidException("Fornecedor");
        }
        if(StringUtils.isBlank(fornecedorEnviado.getNome())){
            fornecedorEnviado.setNome(fornecedorEncontrado.getNome());
        }
        if(fornecedorEnviado.getNome().length() < 2 || fornecedorEnviado.getNome().length() > 40){
            throw new CadastroNotValidException(Codigo.NOME.ordinal(), "O Nome precisa ter de 3 à 40 caracteres!");
        }
        if(fornecedorEnviado.getTelefone() != null && fornecedorEnviado.getTelefone().length() != 11){
            throw new CadastroNotValidException(Codigo.TELEFONE.ordinal(), "Telefone inválido! (XX)XXXXX-XXXX");
        }
        if(fornecedorEnviado.getTelefone() == null || fornecedorEnviado.getTelefone().isBlank()){
            fornecedorEnviado.setTelefone(fornecedorEncontrado.getTelefone().toString());
        }
        Long telefone = convertStringToLong(fornecedorEnviado.getTelefone(), TELEFONE);
        if(fornecedorEnviado.getCnpj() == null){
            fornecedorEnviado.setCnpj(fornecedorEncontrado.getCnpj().toString());
        }
        Long cpnj = Long.parseLong(fornecedorEnviado.getCnpj());

        Optional<Fornecedor> cnpj = fornecedorRepository.findByCnpj(cpnj);
        if(cnpj.isPresent() && !Objects.equals(cnpj.get().getId(), id)) throw new CadastroCnpjAlreadyInUseException();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        fornecedor.setNome(fornecedorEnviado.getNome());
        fornecedor.setTelefone(telefone);
        fornecedor.setCnpj(cpnj);
        fornecedor.setEmail(fornecedorEncontrado.getEmail());
        fornecedor.setNumeroLogradouro(fornecedorEncontrado.getNumeroLogradouro());
        fornecedor.setEndereco(fornecedorEncontrado.getEndereco());

        return fornecedor;
    }
}