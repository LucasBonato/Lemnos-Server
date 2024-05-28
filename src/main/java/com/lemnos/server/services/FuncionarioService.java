package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.exceptions.endereco.EnderecoNotFoundException;
import com.lemnos.server.exceptions.endereco.EntityAlreadyHasEnderecoException;
import com.lemnos.server.exceptions.entidades.funcionario.FuncionarioNotFoundException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.cadastro.Cadastro;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
import com.lemnos.server.models.dtos.responses.IdResponse;
import com.lemnos.server.models.endereco.Endereco;
import com.lemnos.server.models.endereco.Possui.FuncionarioPossuiEndereco;
import com.lemnos.server.models.enums.Codigo;
import com.lemnos.server.models.enums.Situacao;
import com.lemnos.server.models.entidades.Funcionario;
import com.lemnos.server.models.dtos.responses.FuncionarioResponse;
import com.lemnos.server.repositories.cadastro.CadastroRepository;
import com.lemnos.server.repositories.entidades.FuncionarioRepository;
import com.lemnos.server.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuncionarioService extends Util {

    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private CadastroRepository cadastroRepository;

    @Cacheable("allFuncionarios")
    public ResponseEntity<List<FuncionarioResponse>> getAll() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<FuncionarioResponse> dto = new ArrayList<>();
        for(Funcionario funcionario : funcionarios){
            dto.add(new FuncionarioResponse(
                    funcionario.getNome(),
                    funcionario.getCpf(),
                    funcionario.getDataNascimento(),
                    funcionario.getDataAdmissao(),
                    funcionario.getTelefone(),
                    funcionario.getCadastro().getEmail(),
                    funcionario.getCadastro().getSenha(),
                    getEnderecoRecords(funcionario)
            ));
        }
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<FuncionarioResponse> getOneById(Integer id) {
        Funcionario funcionario = getOneFuncionarioById(id);
        FuncionarioResponse record = new FuncionarioResponse(
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getDataNascimento(),
                funcionario.getDataAdmissao(),
                funcionario.getTelefone(),
                funcionario.getCadastro().getEmail(),
                funcionario.getCadastro().getSenha(),
                getEnderecoRecords(funcionario)
        );
        return ResponseEntity.ok(record);
    }


    public ResponseEntity<IdResponse> getByEmail(String email) {
        Cadastro cadastro = cadastroRepository.findByEmail(email).orElseThrow(FuncionarioNotFoundException::new);
        Funcionario funcionario = funcionarioRepository.findByCadastro(cadastro).orElseThrow(FuncionarioNotFoundException::new);
        return ResponseEntity.ok(new IdResponse(funcionario.getId()));
    }

    public ResponseEntity<Void> updateFuncionario(Integer id, FuncionarioRequest funcionarioRequest){
        Funcionario updatedFuncionario = insertData(id, funcionarioRequest);
        funcionarioRepository.save(updatedFuncionario);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteById(Integer id){
        Funcionario funcionarioDeletado = getOneFuncionarioById(id);

        if(funcionarioDeletado.getSituacao() == Situacao.ATIVO) {
            funcionarioDeletado.setSituacao(Situacao.INATIVO);
            funcionarioRepository.save(funcionarioDeletado);
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> createEndereco(Integer id, EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioById(id);
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> fpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), id);
        if(fpeOptional.isPresent()) throw new EntityAlreadyHasEnderecoException("Funcionário");

        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> updateEndereco(Integer id, EnderecoRequest enderecoRequest) {
        Funcionario funcionario = getOneFuncionarioById(id);
        Endereco endereco = getEndereco(enderecoRequest);

        Optional<FuncionarioPossuiEndereco> cpeOptional = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(endereco.getCep(), id);
        if(cpeOptional.isEmpty()) throw new EnderecoNotFoundException("Cliente");

        funcionarioPossuiEnderecoRepository.save(new FuncionarioPossuiEndereco(funcionario, endereco, enderecoRequest.numeroLogradouro(), enderecoRequest.complemento()));

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> removeEndereco(Integer id, String cep) {
        getOneFuncionarioById(id);
        FuncionarioPossuiEndereco fpe = funcionarioPossuiEnderecoRepository.findByCepAndId_Cliente(cep, id).orElseThrow(() -> new EnderecoNotFoundException("Funcionário"));
        funcionarioPossuiEnderecoRepository.delete(fpe);
        return ResponseEntity.ok().build();
    }

    private Funcionario getOneFuncionarioById(Integer id) {
        return funcionarioRepository.findById(id).orElseThrow(FuncionarioNotFoundException::new);
    }
    private static List<EnderecoResponse> getEnderecoRecords(Funcionario funcionario) {
        List<EnderecoResponse> enderecoResponses = new ArrayList<>();
        for(FuncionarioPossuiEndereco fpe : funcionario.getEnderecos()){
            enderecoResponses.add(new EnderecoResponse(
                    fpe.getEndereco().getCep(),
                    fpe.getEndereco().getLogradouro(),
                    fpe.getNumeroLogradouro(),
                    fpe.getComplemento(),
                    fpe.getEndereco().getCidade().getCidade(),
                    fpe.getEndereco().getBairro(),
                    fpe.getEndereco().getEstado().getUf()
            ));
        }
        return enderecoResponses;
    }
    private Funcionario insertData(Integer id, FuncionarioRequest funcionarioEnviado) {
        Funcionario funcionarioEncontrado = getOneFuncionarioById(id);

        Date dataNasc;
        Date dataAdmi;

        if(StringUtils.isBlank(funcionarioEnviado.nome()) && StringUtils.isBlank(funcionarioEnviado.cpf()) && StringUtils.isBlank(funcionarioEnviado.dataAdmissao()) && StringUtils.isBlank(funcionarioEnviado.dataNascimento()) && StringUtils.isBlank(funcionarioEnviado.telefone())){
            throw new UpdateNotValidException("Funcionário");
        }
        if(StringUtils.isBlank(funcionarioEnviado.nome())){
            funcionarioEnviado = funcionarioEnviado.setNome(funcionarioEncontrado.getNome());
        }
        if(StringUtils.isBlank(funcionarioEnviado.cpf())){
            funcionarioEnviado = funcionarioEnviado.setCpf(funcionarioEncontrado.getCpf().toString());
        }
        Long cpf = convertStringToLong(funcionarioEnviado.cpf(), Codigo.CPF);
        if(StringUtils.isBlank(funcionarioEnviado.dataNascimento())){
            dataNasc = funcionarioEncontrado.getDataNascimento();
        } else {
            dataNasc = convertData(funcionarioEnviado.dataNascimento());
        }
        if(StringUtils.isBlank(funcionarioEnviado.dataAdmissao())){
            dataAdmi = funcionarioEncontrado.getDataAdmissao();
        } else {
            dataAdmi = convertData(funcionarioEnviado.dataAdmissao());
        }
        if(StringUtils.isBlank(funcionarioEnviado.telefone())){
            funcionarioEnviado = funcionarioEnviado.setTelefone(funcionarioEncontrado.getTelefone().toString());
        }
        Long telefone = convertStringToLong(funcionarioEnviado.telefone(), Codigo.TELEFONE);

        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(cpf);
        if(funcionarioOptional.isPresent() && !Objects.equals(funcionarioOptional.get().getId(), id)) throw new CadastroCpfAlreadyInUseException();

        Funcionario updatedFuncionario = new Funcionario();
        updatedFuncionario.setId(id);
        updatedFuncionario.setNome(funcionarioEnviado.nome());
        updatedFuncionario.setCpf(cpf);
        updatedFuncionario.setDataNascimento(dataNasc);
        updatedFuncionario.setDataAdmissao(dataAdmi);
        updatedFuncionario.setTelefone(telefone);
        updatedFuncionario.setCadastro(funcionarioEncontrado.getCadastro());
        updatedFuncionario.setEnderecos(funcionarioEncontrado.getEnderecos());

        return updatedFuncionario;
    }
}