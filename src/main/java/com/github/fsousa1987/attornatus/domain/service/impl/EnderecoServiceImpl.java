package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.EnderecoJaCadastradoException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.EnderecoNaoEncontradoException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoLoteException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoLoteResponse enderecoLoteResponse;
    private final EnderecoRepository enderecoRepository;
    private final ModelMapper enderecoMapper;

    @Transactional
    @Override
    public EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest) {
        var enderecosAchados = buscarPessoaOuFalhar(idPessoa);

        var enderecoEntity = enderecoMapper.map(enderecoRequest, EnderecoEntity.class);

        verificarEnderecoIgual(enderecosAchados, enderecoEntity);
        verificarEnderecoPrincipal(enderecoEntity, enderecosAchados);
        PessoaEntity pessoa = setarIdParaPessoa(idPessoa);

        enderecoEntity.setPessoa(pessoa);
        enderecoRepository.save(enderecoEntity);

        return enderecoMapper.map(enderecoEntity, EnderecoResponse.class);
    }

    @Transactional
    @Override
    public EnderecoLoteResponse adicionarEnderecosEmLote(Long idPessoa, Set<EnderecoRequest> enderecosRequest) {
        var enderecosAchados = buscarPessoaOuFalhar(idPessoa);

        var enderecosElegiveisPersistencia = processarEnderecosElegiveis(enderecosRequest, enderecosAchados);

        if (!enderecosElegiveisPersistencia.isEmpty()) {
            enderecosElegiveisPersistencia.forEach(endereco -> endereco.setIsPrincipal(false));
            var enderecosSalvos = persistirEnderecosElegiveis(idPessoa, enderecosElegiveisPersistencia);
            montarResponse(enderecosSalvos);
            return enderecoLoteResponse;
        }

        throw new InvalidEnderecoLoteException("Não existe endereços novos para adicionar");
    }

    @Transactional(readOnly = true)
    @Override
    public EnderecoLoteResponse listarEnderecos(Long idPessoa) {
        var enderecos = buscarPessoaOuFalhar(idPessoa);
        montarResponse(enderecos);
        return enderecoLoteResponse;
    }

    @Transactional
    @Override
    public EnderecoResponse alterarPrincipal(Long idEndereco) {
        var enderecoAchado = buscarEnderecoOuFalhar(idEndereco);

        var enderecos = enderecoAchado.getPessoa().getEnderecos();
        enderecos.stream().filter(EnderecoEntity::getIsPrincipal).forEach(endereco -> endereco.setIsPrincipal(false));

        enderecoAchado.setIsPrincipal(true);
        var enderecoAtualizado = enderecoRepository.save(enderecoAchado);

        return enderecoMapper.map(enderecoAtualizado, EnderecoResponse.class);
    }

    @Transactional
    @Override
    public EnderecoResponse atualizarEndereco(Long idEndereco, EnderecoRequest enderecoRequest) {
        var enderecoEncontrado = buscarEnderecoOuFalhar(idEndereco);

        var enderecoEntity = enderecoMapper.map(enderecoRequest, EnderecoEntity.class);

        if (enderecoEncontrado.equals(enderecoEntity)) {
            throw new EnderecoJaCadastradoException("Endereço já existente na base de dados");
        }

        BeanUtils.copyProperties(enderecoRequest, enderecoEncontrado);
        var enderecoAtualizado = enderecoRepository.save(enderecoEncontrado);
        return enderecoMapper.map(enderecoAtualizado, EnderecoResponse.class);
    }

    private PessoaEntity setarIdParaPessoa(Long idPessoa) {
        return PessoaEntity.builder()
                .id(idPessoa)
                .build();
    }

    private void verificarEnderecoPrincipal(EnderecoEntity enderecoEntity, List<EnderecoEntity> enderecosAchados) {
        if (enderecoEntity.getIsPrincipal()) {
            enderecosAchados.forEach(endereco -> endereco.setIsPrincipal(false));
        }
    }

    private void verificarEnderecoIgual(List<EnderecoEntity> enderecosAchados, EnderecoEntity enderecoEntity) {
        enderecosAchados.forEach(enderecoAchado -> {
            if (enderecoEntity.equals(enderecoAchado)) {
                throw new EnderecoJaCadastradoException("O endereço já está cadastrado");
            }
        });
    }

    private void montarResponse(List<EnderecoEntity> enderecosSalvos) {
        var enderecoResponseList = enderecosSalvos
                .stream()
                .map(endereco -> enderecoMapper
                        .map(endereco, EnderecoResponse.class))
                .toList();

        enderecoLoteResponse.setEnderecos(enderecoResponseList);
    }

    private List<EnderecoEntity> buscarPessoaOuFalhar(Long idPessoa) {
        var enderecosAchados = enderecoRepository.findByPessoaId(idPessoa);

        if (enderecosAchados.isEmpty()) {
            throw new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + idPessoa);
        }

        return enderecosAchados;
    }

    private List<EnderecoEntity> persistirEnderecosElegiveis(Long idPessoa,
                                                             List<EnderecoEntity> enderecosElegiveisPersistencia) {
        var pessoaEntity = setarIdParaPessoa(idPessoa);
        enderecosElegiveisPersistencia.forEach(enderecoElegivel -> enderecoElegivel.setPessoa(pessoaEntity));
        return enderecoRepository.saveAll(enderecosElegiveisPersistencia);
    }

    private List<EnderecoEntity> processarEnderecosElegiveis(Set<EnderecoRequest> enderecosRequests,
                                                             List<EnderecoEntity> enderecosAchados) {
        return enderecosRequests.stream()
                .map(endereco -> enderecoMapper.map(endereco, EnderecoEntity.class))
                .filter(endereco -> !enderecosAchados.contains(endereco))
                .toList();
    }

    private EnderecoEntity buscarEnderecoOuFalhar(Long idEndereco) {
        return enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(
                        "Endereço não encontrado para o id: " + idEndereco));
    }

}