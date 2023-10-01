package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.AdicionarEnderecosLoteRequest;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

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
    public List<EnderecoResponse> adicionarEnderecosEmLote(Long idPessoa, Set<AdicionarEnderecosLoteRequest> enderecosRequest) {
        List<EnderecoEntity> enderecosAchados = buscarEnderecosOuFalhar(idPessoa);

        List<EnderecoEntity> enderecosEntity = new ArrayList<>();
        converterEnderecoRequestEmEnderecoEntity(enderecosRequest, enderecosEntity);

        List<EnderecoEntity> enderecosElegiveisPersistencia = compararEnderecosIguais(enderecosEntity, enderecosAchados);

        if (!enderecosElegiveisPersistencia.isEmpty()) {
            return processarPersistenciaEnderecosEmLote(enderecosElegiveisPersistencia, enderecosAchados);
        }

        return enderecoMapper.toListEnderecoResponse(enderecosAchados);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EnderecoResponse> listarEnderecos(Long idPessoa) {
        List<EnderecoEntity> enderecos = buscarEnderecosOuFalhar(idPessoa);

        return enderecoMapper.toListEnderecoResponse(enderecos);
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

    private void converterEnderecoRequestEmEnderecoEntity(Set<AdicionarEnderecosLoteRequest> enderecosRequests, List<EnderecoEntity> enderecos) {
        enderecosRequests.forEach(endereco -> enderecos.add(enderecoMapper.toEnderecoEntity(endereco)));
    }

    private List<EnderecoEntity> compararEnderecosIguais(List<EnderecoEntity> enderecos, List<EnderecoEntity> enderecosAchados) {
        return enderecos.stream().filter(endereco -> !enderecosAchados.contains(endereco)).toList();
    }

    private List<EnderecoResponse> processarPersistenciaEnderecosEmLote(List<EnderecoEntity> enderecosElegiveis, List<EnderecoEntity> enderecosAchados) {
        enderecosElegiveis.forEach(endereco -> endereco.setIsPrincipal(false));

        PessoaEntity pessoaEntity = extrairPessoaDoEndereco(enderecosAchados);
        enderecosElegiveis.forEach(endereco -> endereco.setPessoa(pessoaEntity));
        enderecosAchados.addAll(enderecosElegiveis);
        List<EnderecoEntity> enderecoEntities = enderecoRepository.saveAll(enderecosAchados);
        return enderecoMapper.toListEnderecoResponse(enderecoEntities);
    }

}
