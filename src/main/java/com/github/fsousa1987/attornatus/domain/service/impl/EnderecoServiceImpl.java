package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.EnderecoNaoEncontradoException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoLoteException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoPrincipalException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.EnderecoLoteRequest;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoLoteResponse enderecoLoteResponse;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Transactional
    @Override
    public EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest) {
        List<EnderecoEntity> enderecosAchados = buscarEnderecosOuFalhar(idPessoa);

        verificarAlteracaoEnderecoPrincipal(enderecoRequest, enderecosAchados);
        PessoaEntity pessoa = extrairPessoaDoEndereco(enderecosAchados);

        EnderecoEntity enderecoEntity = enderecoMapper.toEnderecoEntity(enderecoRequest);
        enderecoEntity.setPessoa(pessoa);

        enderecosAchados.add(enderecoEntity);
        List<EnderecoEntity> savedEnderecos = enderecoRepository.saveAll(enderecosAchados);

        EnderecoEntity endereco = localizarUltimoEnderecoSalvo(savedEnderecos);
        return enderecoMapper.toEnderecoResponse(endereco);
    }

    @Transactional
    @Override
    public EnderecoLoteResponse adicionarEnderecosEmLote(Long idPessoa, Set<EnderecoLoteRequest> enderecosRequest) {
        List<EnderecoEntity> enderecosAchados = buscarEnderecosOuFalhar(idPessoa);

        List<EnderecoEntity> enderecosEntity = new ArrayList<>();
        converterEnderecoRequestEmEnderecoEntity(enderecosRequest, enderecosEntity);
        PessoaEntity pessoaEntity = extrairPessoaDoEndereco(enderecosAchados);

        List<EnderecoEntity> enderecosElegiveisPersistencia = compararEnderecosIguais(enderecosEntity, enderecosAchados);

        if (!enderecosElegiveisPersistencia.isEmpty()) {
            return processarPersistenciaEnderecosEmLote(enderecosElegiveisPersistencia, pessoaEntity);
        }

        throw new InvalidEnderecoLoteException("Não existe endereços novos para adicionar");
    }

    @Transactional(readOnly = true)
    @Override
    public EnderecoLoteResponse listarEnderecos(Long idPessoa) {

        List<EnderecoEntity> enderecos = buscarEnderecosOuFalhar(idPessoa);

        List<EnderecoResponse> enderecoResponse = enderecoMapper.toListEnderecoResponse(enderecos);
        enderecoLoteResponse.setEnderecos(enderecoResponse);
        return enderecoLoteResponse;
    }

    @Transactional
    @Override
    public EnderecoResponse atualizarEndereco(Long idEndereco, EnderecoRequest enderecoRequest) {
        EnderecoEntity enderecoEncontrado = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado para o id: " + idEndereco));

        verificarPossibilidadeDeAtualizacao(enderecoEncontrado, enderecoRequest);

        if (enderecoEncontrado.getIsPrincipal() == enderecoRequest.getIsPrincipal()) {
            BeanUtils.copyProperties(enderecoRequest, enderecoEncontrado);
            EnderecoEntity enderecoAtualizado = enderecoRepository.save(enderecoEncontrado);
            return enderecoMapper.toEnderecoResponse(enderecoAtualizado);
        }

        EnderecoEntity enderecoAtualizado = setNovoEnderecoPrincipalAoAtualizar(enderecoRequest, enderecoEncontrado);
        return enderecoMapper.toEnderecoResponse(enderecoAtualizado);
    }

    private void verificarAlteracaoEnderecoPrincipal(EnderecoRequest enderecoRequest, List<EnderecoEntity> enderecos) {
        if (enderecoRequest.getIsPrincipal()) {
            enderecos.forEach(pessoa -> pessoa.setIsPrincipal(false));
        }
    }

    private EnderecoEntity localizarUltimoEnderecoSalvo(List<EnderecoEntity> enderecos) {
        int listSize = enderecos.size();
        return enderecos.get(listSize - 1);
    }

    private PessoaEntity extrairPessoaDoEndereco(List<EnderecoEntity> enderecosAchados) {
        return enderecosAchados.get(0).getPessoa();
    }

    private List<EnderecoEntity> buscarEnderecosOuFalhar(Long idPessoa) {
        List<EnderecoEntity> enderecosAchados = enderecoRepository.findByPessoaId(idPessoa);

        if (enderecosAchados.isEmpty()) {
            throw new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + idPessoa);
        }

        return enderecosAchados;
    }

    private void converterEnderecoRequestEmEnderecoEntity(Set<EnderecoLoteRequest> enderecosRequests, List<EnderecoEntity> enderecos) {
        enderecosRequests.forEach(endereco -> enderecos.add(enderecoMapper.toEnderecoEntity(endereco)));
    }

    private List<EnderecoEntity> compararEnderecosIguais(List<EnderecoEntity> enderecos, List<EnderecoEntity> enderecosAchados) {
        return enderecos.stream().filter(endereco -> !enderecosAchados.contains(endereco)).toList();
    }

    private EnderecoLoteResponse processarPersistenciaEnderecosEmLote(List<EnderecoEntity> enderecosElegiveis, PessoaEntity pessoaEntity) {
        enderecosElegiveis.forEach(endereco -> {
            endereco.setIsPrincipal(false);
            endereco.setPessoa(pessoaEntity);
        });
        List<EnderecoEntity> enderecoEntities = enderecoRepository.saveAll(enderecosElegiveis);
        List<EnderecoResponse> enderecos = enderecoMapper.toEnderecoLoteResponse(enderecoEntities);

        enderecoLoteResponse.setEnderecos(enderecos);
        return enderecoLoteResponse;
    }

    private void verificarPossibilidadeDeAtualizacao(EnderecoEntity enderecoEntity, EnderecoRequest enderecoRequest) {
        if (enderecoEntity.getIsPrincipal() && !enderecoRequest.getIsPrincipal()) {
            throw new InvalidEnderecoPrincipalException("Deve haver pelo menos um endereço principal");
        }
    }

    private EnderecoEntity setNovoEnderecoPrincipalAoAtualizar(EnderecoRequest enderecoRequest, EnderecoEntity enderecoEntity) {
        BeanUtils.copyProperties(enderecoRequest, enderecoEntity);
        List<EnderecoEntity> enderecos = enderecoEntity.getPessoa().getEnderecos();
        enderecos.forEach(endereco -> endereco.setIsPrincipal(false));
        enderecoEntity.setIsPrincipal(true);
        return enderecoRepository.save(enderecoEntity);
    }

}
