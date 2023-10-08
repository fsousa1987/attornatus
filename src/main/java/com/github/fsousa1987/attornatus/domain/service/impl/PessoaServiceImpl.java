package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.SemEnderecoPrincipalException;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponseList;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import com.github.fsousa1987.attornatus.domain.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final ModelMapper mapper;

    @Transactional
    @Override
    public PessoaResponse salvarPessoa(PessoaRequest request) {
        validarExistenciaEnderecoPrincipal(request);

        var pessoaEntity = mapper.map(request, PessoaEntity.class);
        var pessoaSalva = pessoaRepository.save(pessoaEntity);

        pessoaSalva.getEnderecos().forEach(endereco -> {
            var pessoa = PessoaEntity
                    .builder()
                    .id(pessoaSalva.getId())
                    .build();
            endereco.setPessoa(pessoa);
        });

        enderecoRepository.saveAll(pessoaSalva.getEnderecos());
        return mapper.map(pessoaSalva, PessoaResponse.class);
    }

    @Transactional
    @Override
    public void atualizarPessoa(Long id, PessoaRequest request) {
        var pessoaEntity = buscarOuFalhar(id);
        BeanUtils.copyProperties(request, pessoaEntity);
        pessoaRepository.save(pessoaEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public PessoaResponse buscarPorId(Long id) {
        var pessoaEntity = buscarOuFalhar(id);
        return mapper.map(pessoaEntity, PessoaResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PessoaResponseList buscarTodas() {
        var pessoasEntity = pessoaRepository.findAll();

        var pessoasList = pessoasEntity
                .stream()
                .map(pessoa -> mapper.map(pessoa, PessoaResponse.class))
                .toList();

        return PessoaResponseList
                .builder()
                .pessoas(pessoasList)
                .build();
    }

    private PessoaEntity buscarOuFalhar(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + id));
    }

    private void validarExistenciaEnderecoPrincipal(PessoaRequest salvarPessoaRequest) {
        var enderecos = salvarPessoaRequest.listaDeEnderecos();

        var count = enderecos.stream().filter(EnderecoRequest::isEnderecoPrincipal).count();
        if (count != 1) {
            throw new SemEnderecoPrincipalException("Não foi encontrado endereço principal ou " +
                    "foi encontrado mais do que um");
        }
    }

}
