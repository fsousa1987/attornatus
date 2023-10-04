package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoPrincipalException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.core.mapper.PessoaMapper;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final PessoaMapper pessoaMapper;
    private final EnderecoMapper enderecoMapper;

    @Transactional
    @Override
    public PessoaResponse salvarPessoa(PessoaRequest request) {
        List<EnderecoRequest> enderecos = validarExistenciaEnderecoPrincipal(request);

        var pessoaEntity = pessoaMapper.toPessoaEntity(request);
        var listaEnderecosEntity = enderecoMapper.toListEnderecoEntity(enderecos);

        pessoaEntity.setEnderecos(listaEnderecosEntity);

        var pessoaSalva = pessoaRepository.save(pessoaEntity);

        pessoaSalva.getEnderecos().forEach(endereco -> {
            var pessoa = PessoaEntity
                    .builder()
                    .id(pessoaSalva.getId())
                    .build();
            endereco.setPessoa(pessoa);
        });

        enderecoRepository.saveAll(pessoaSalva.getEnderecos());
        return pessoaMapper.toPessoaResponse(pessoaSalva);
    }

    @Transactional
    @Override
    public void atualizarPessoa(Long id, PessoaRequest atualizarPessoaRequest) {
        PessoaEntity pessoaEntity = buscarOuFalhar(id);
        BeanUtils.copyProperties(atualizarPessoaRequest, pessoaEntity);
        pessoaRepository.save(pessoaEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public PessoaResponse buscarPorId(Long id) {
        PessoaEntity pessoaEntity = buscarOuFalhar(id);
        return pessoaMapper.toPessoaResponse(pessoaEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PessoaResponse> buscarTodas() {
        List<PessoaEntity> pessoasEntity = pessoaRepository.findAll();
        return pessoaMapper.toListPessoaResponse(pessoasEntity);
    }

    private PessoaEntity buscarOuFalhar(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + id));
    }

    private List<EnderecoRequest> validarExistenciaEnderecoPrincipal(PessoaRequest salvarPessoaRequest) {
        List<EnderecoRequest> enderecos = salvarPessoaRequest.listaDeEnderecos();

        long count = enderecos.stream().filter(EnderecoRequest::getIsPrincipal).count();
        if (count != 1) {
            throw new InvalidEnderecoPrincipalException("Precisa ter um endereço principal");
        }

        return enderecos;
    }

}
