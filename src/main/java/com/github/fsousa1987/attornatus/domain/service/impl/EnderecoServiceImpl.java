package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
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

    @Transactional
    public EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest) {
        PessoaEntity pessoaEntity = getPessoaEntity(idPessoa);

        verificarAlteracaoEnderecoPrincipal(enderecoRequest, pessoaEntity);

        pessoaEntity.getEnderecos().add(enderecoMapper.toEnderecoEntity(enderecoRequest));
        pessoaEntity.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaEntity));

        PessoaEntity pessoaSalva = pessoaRepository.save(pessoaEntity);
        EnderecoEntity enderecoEntity = localizarUltimoEnderecoSalvo(pessoaSalva);

        return enderecoMapper.toEnderecoResponse(enderecoEntity);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponse> listarEnderecos(Long idPessoa) {
        PessoaEntity pessoaEntity = getPessoaEntity(idPessoa);
        return enderecoMapper.toListEnderecoResponse(pessoaEntity.getEnderecos());
    }

    private PessoaEntity getPessoaEntity(Long idPessoa) {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + idPessoa));
    }

    private void verificarAlteracaoEnderecoPrincipal(EnderecoRequest enderecoRequest, PessoaEntity pessoaEntity) {
        if (enderecoRequest.getIsPrincipal()) {
            pessoaEntity.getEnderecos().forEach(pessoa -> pessoa.setIsPrincipal(false));
        }
    }

    private EnderecoEntity localizarUltimoEnderecoSalvo(PessoaEntity pessoaSalva) {
        int listSize = pessoaSalva.getEnderecos().size();
        return pessoaSalva.getEnderecos().get(listSize - 1);
    }

}
