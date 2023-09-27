package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest) {
        PessoaEntity pessoaEntity = getPessoaEntity(idPessoa);

        if (enderecoRequest.getIsPrincipal()) {
            pessoaEntity.getEnderecos().forEach(pessoa -> pessoa.setIsPrincipal(false));
        }

        EnderecoEntity enderecoEntity = enderecoMapper.toEnderecoEntity(enderecoRequest);
        EnderecoEntity savedEndereco = enderecoRepository.save(enderecoEntity);

        pessoaEntity.getEnderecos().add(savedEndereco);

        pessoaRepository.save(pessoaEntity);
        return enderecoMapper.toEnderecoResponse(enderecoEntity);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponse> listarEnderecos(Long idPessoa) {
        PessoaEntity pessoaEntity = getPessoaEntity(idPessoa);
        List<EnderecoEntity> enderecos = pessoaEntity.getEnderecos();
        return enderecoMapper.toListEnderecoResponse(enderecos);
    }

    private PessoaEntity getPessoaEntity(Long idPessoa) {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + idPessoa));
    }

}
