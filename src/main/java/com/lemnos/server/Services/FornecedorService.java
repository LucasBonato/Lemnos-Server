package com.lemnos.server.Services;

import com.lemnos.server.Exceptions.Cadastro.CadastroCnpjAlreadyInUseException;
import com.lemnos.server.Exceptions.Cadastro.CadastroNotValidException;
import com.lemnos.server.Exceptions.Fornecedor.FornecedorNotFoundException;
import com.lemnos.server.Exceptions.Global.CnpjNotValidException;
import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import com.lemnos.server.Exceptions.Global.TelefoneNotValidException;
import com.lemnos.server.Exceptions.Global.UpdateNotValidException;
import com.lemnos.server.Models.Cliente;
import com.lemnos.server.Models.DTOs.FornecedorDTO;
import com.lemnos.server.Models.Enums.Codigo;
import com.lemnos.server.Models.Enums.Situacao;
import com.lemnos.server.Models.Fornecedor;
import com.lemnos.server.Repositories.FornecedorRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired private FornecedorRepository fornecedorRepository;

    public ResponseEntity<List<Fornecedor>> getAll() {
        return ResponseEntity.ok(fornecedorRepository.findAll());
    }

    public ResponseEntity<Fornecedor> getOneById(Integer id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if(fornecedorOptional.isPresent()){
            return ResponseEntity.ok(fornecedorOptional.get());
        }
        throw new FornecedorNotFoundException();
    }

    public ResponseEntity updateFornecedor(Integer id, FornecedorDTO fornecedorDTO) {
        Fornecedor updatedFornecedor = insertData(id, fornecedorDTO);
        fornecedorRepository.save(updatedFornecedor);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteById(Integer id) {
        Fornecedor fornecedorDeletado = getOneById(id).getBody();

        assert fornecedorDeletado != null;
        if(fornecedorDeletado.getSituacao() == Situacao.ATIVO) {
            fornecedorDeletado.setSituacao(Situacao.INATIVO);
            fornecedorRepository.save(fornecedorDeletado);
        }
        return ResponseEntity.ok().build();
    }

    private Fornecedor insertData(Integer id, FornecedorDTO fornecedorEnviado){
        Long CNPJ, Telefone;
        Fornecedor fornecedorEncontrado = getOneById(id).getBody();
        assert fornecedorEncontrado != null;
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
        try {
            Telefone = Long.parseLong(fornecedorEnviado.getTelefone());
        } catch (NumberFormatException e) {
            throw new TelefoneNotValidException("Telefone inválido: utilize só números");
        }
        if(fornecedorEnviado.getCnpj() == null){
            fornecedorEnviado.setCnpj(fornecedorEncontrado.getCnpj().toString());
        }
        try {
            CNPJ = Long.parseLong(fornecedorEnviado.getCnpj());
        } catch (NumberFormatException e) {
            throw new CnpjNotValidException("CNPJ inválido: utilize só números");
        }

        Optional<Fornecedor> cnpj = fornecedorRepository.findByCnpj(CNPJ);
        if(cnpj.isPresent() && !Objects.equals(cnpj.get().getId(), id)) throw new CadastroCnpjAlreadyInUseException();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        fornecedor.setNome(fornecedorEnviado.getNome());
        fornecedor.setTelefone(Telefone);
        fornecedor.setCnpj(CNPJ);
        fornecedor.setEmail(fornecedorEncontrado.getEmail());
        fornecedor.setNumeroLogradouro(fornecedorEncontrado.getNumeroLogradouro());
        fornecedor.setEnderecos(fornecedorEncontrado.getEnderecos());

        return fornecedor;
    }
}
