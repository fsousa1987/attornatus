package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoPrincipalException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.AtualizarPessoaRequest;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.core.mapper.PessoaMapper;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import com.github.fsousa1987.attornatus.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    @Transactional
    public PessoaResponse salvarPessoa(SalvarPessoaRequest salvarPessoaRequest) {
        validarExistenciaEnderecoPrincipal(salvarPessoaRequest);

        final PessoaEntity pessoaEntity = pessoaMapper.toPessoaEntity(salvarPessoaRequest);
        pessoaEntity.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaEntity));
        PessoaEntity pessoaSalva = pessoaRepository.save(pessoaEntity);
        return pessoaMapper.toPessoaResponse(pessoaSalva);
    }

    @Transactional
    public void atualizarPessoa(Long id, AtualizarPessoaRequest atualizarPessoaRequest) {
        PessoaEntity pessoaEntity = buscarOuFalhar(id);
        BeanUtils.copyProperties(atualizarPessoaRequest, pessoaEntity);
        pessoaRepository.save(pessoaEntity);
    }

    @Transactional(readOnly = true)
    public PessoaResponse buscarPorId(Long id) {
        PessoaEntity pessoaEntity = buscarOuFalhar(id);
        return pessoaMapper.toPessoaResponse(pessoaEntity);
    }

    @Transactional(readOnly = true)
    public List<PessoaResponse> buscarTodas() {
        List<PessoaEntity> pessoasEntity = pessoaRepository.findAll();
        return pessoaMapper.toListPessoaResponse(pessoasEntity);
    }

    private PessoaEntity buscarOuFalhar(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + id));
    }

    private void validarExistenciaEnderecoPrincipal(SalvarPessoaRequest salvarPessoaRequest) {
        List<EnderecoRequest> enderecos = salvarPessoaRequest.getEnderecos();

        long count = enderecos.stream().filter(EnderecoRequest::getIsPrincipal).count();
        if (count > 1 || count == 0) {
            throw new InvalidEnderecoPrincipalException("Precisa ter um endereço principal");
        }
    }

}
