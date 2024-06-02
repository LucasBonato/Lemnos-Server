package com.lemnos.server.services;

import com.lemnos.server.exceptions.cadastro.CadastroCpfAlreadyInUseException;
import com.lemnos.server.exceptions.entidades.funcionario.FuncionarioNotFoundException;
import com.lemnos.server.exceptions.global.UpdateNotValidException;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.EnderecoResponse;
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
            dto.add(getFuncionarioResponse(funcionario));
        }
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<FuncionarioResponse> getOneByEmail(String email) {
        Funcionario funcionario = getOneFuncionarioByEmail(email);
        FuncionarioResponse record = getFuncionarioResponse(funcionario);
        return ResponseEntity.ok(record);
    }

    public ResponseEntity<Void> updateFuncionario(String email, FuncionarioRequest funcionarioRequest){
        Funcionario updatedFuncionario = insertData(email, funcionarioRequest);
        funcionarioRepository.save(updatedFuncionario);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteByEmail(String email){
        Funcionario funcionarioDeletado = getOneFuncionarioByEmail(email);

        if(funcionarioDeletado.getSituacao() == Situacao.ATIVO) {
            funcionarioDeletado.setSituacao(Situacao.INATIVO);
            funcionarioRepository.save(funcionarioDeletado);
        }
        return ResponseEntity.noContent().build();
    }

    private static FuncionarioResponse getFuncionarioResponse(Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getNome(),
                funcionario.getDataNascimento(),
                funcionario.getDataAdmissao(),
                funcionario.getTelefone(),
                funcionario.getCadastro().getEmail(),
                funcionario.getSituacao().toString(),
                getEnderecoRecords(funcionario)
        );
    }

    private Funcionario getOneFuncionarioByEmail(String email) {
        return funcionarioRepository.findByCadastro(
                cadastroRepository.findByEmail(email).orElseThrow(FuncionarioNotFoundException::new)
        ).orElseThrow(FuncionarioNotFoundException::new);
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
    private Funcionario insertData(String email, FuncionarioRequest funcionarioEnviado) {
        Funcionario funcionarioEncontrado = getOneFuncionarioByEmail(email);

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
        if(funcionarioOptional.isPresent() && !Objects.equals(funcionarioOptional.get().getId(), funcionarioEncontrado.getId())) throw new CadastroCpfAlreadyInUseException();

        Funcionario updatedFuncionario = new Funcionario();
        updatedFuncionario.setId(funcionarioEncontrado.getId());
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