package com.github.fsousa1987.attornatus.domain.service.impl;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.EnderecoJaCadastradoException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoLoteResponse enderecoLoteResponse;
    private final EnderecoRepository enderecoRepository;
    private final PessoaRepository pessoaRepository;
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

    private PessoaEntity setarIdParaPessoa(Long idPessoa) {
        return PessoaEntity.builder()
                .id(idPessoa)
                .build();
    }

    private void verificarEnderecoPrincipal(EnderecoEntity enderecoEntity, List<EnderecoEntity> enderecosAchados) {
        if (enderecoEntity.getIsPrincipal()) {
            enderecosAchados.forEach(EnderecoEntity::mudarStatusEnderecoPrincipal);
        }
    }

    private void verificarEnderecoIgual(List<EnderecoEntity> enderecosAchados, EnderecoEntity enderecoEntity) {
        enderecosAchados.forEach(enderecoAchado -> {
            if (enderecoEntity.equals(enderecoAchado)) {
                throw new EnderecoJaCadastradoException("O endereço já está cadastrado");
            }
        });
    }

    @Override
    public EnderecoLoteResponse adicionarEnderecosEmLote(Long idPessoa, Set<EnderecoRequest> enderecos) {
        return null;
    }

    @Override
    public EnderecoLoteResponse listarEnderecos(Long idPessoa) {
        return null;
    }

    @Override
    public EnderecoResponse atualizarEndereco(Long idEndereco, EnderecoRequest enderecoRequest) {
        return null;
    }

    @Override
    public EnderecoResponse alterarPrincipal(Long idEndereco) {
        return null;
    }

    //    @Transactional
//    @Override
//    public EnderecoLoteResponse adicionarEnderecosEmLote(Long idPessoa, Set<EnderecoRequest> enderecosRequest) {
//        List<EnderecoEntity> enderecosAchados = buscarPessoasOuFalhar(idPessoa);
//
//        List<EnderecoEntity> enderecosEntity = new ArrayList<>();
//        converterEnderecoRequestEmEnderecoEntity(enderecosRequest, enderecosEntity);
//        PessoaEntity pessoaEntity = extrairPessoaDoEndereco(enderecosAchados);
//
//        List<EnderecoEntity> enderecosElegiveisPersistencia = compararEnderecosIguais(enderecosEntity, enderecosAchados);
//
//        if (!enderecosElegiveisPersistencia.isEmpty()) {
//            return processarPersistenciaEnderecosEmLote(enderecosElegiveisPersistencia, pessoaEntity);
//        }
//
//        throw new InvalidEnderecoLoteException("Não existe endereços novos para adicionar");
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public EnderecoLoteResponse listarEnderecos(Long idPessoa) {
//
//        List<EnderecoEntity> enderecos = buscarPessoasOuFalhar(idPessoa);
//
//        List<EnderecoResponse> enderecoResponse = enderecoMapper.toListEnderecoResponse(enderecos);
//        enderecoLoteResponse.setEnderecos(enderecoResponse);
//        return enderecoLoteResponse;
//    }
//
//    @Transactional
//    @Override
//    public EnderecoResponse atualizarEndereco(Long idEndereco, EnderecoRequest enderecoRequest) {
//        EnderecoEntity enderecoEncontrado = buscarEnderecoOuFalhar(idEndereco);
//
//        EnderecoEntity enderecoEntity = enderecoMapper.toEnderecoEntity(enderecoRequest);
//
//        if (enderecoEncontrado.equals(enderecoEntity)) {
//            throw new InvalidEnderecoLoteException("Endereço já existente na base de dados");
//        }
//
//        BeanUtils.copyProperties(enderecoRequest, enderecoEncontrado);
//        EnderecoEntity enderecoAtualizado = enderecoRepository.save(enderecoEncontrado);
//        return enderecoMapper.toEnderecoResponse(enderecoAtualizado);
//    }
//
//    @Transactional
//    @Override
//    public EnderecoResponse alterarPrincipal(Long idEndereco) {
//        EnderecoEntity enderecoAchado = buscarEnderecoOuFalhar(idEndereco);
//
//        List<EnderecoEntity> enderecos = enderecoAchado.getPessoa().getEnderecos();
//        enderecos.forEach(endereco -> endereco.setIsPrincipal(false));
//
//        enderecoAchado.setIsPrincipal(true);
//        EnderecoEntity enderecoAtualizado = enderecoRepository.save(enderecoAchado);
//
//        return enderecoMapper.toEnderecoResponse(enderecoAtualizado);
//    }
//
//    private void verificarAlteracaoEnderecoPrincipal(EnderecoRequest enderecoRequest, List<EnderecoEntity> enderecos) {
//        if (enderecoRequest.isEnderecoPrincipal()) {
//            enderecos.forEach(pessoa -> pessoa.setIsPrincipal(false));
//        }
//    }
//
//    private EnderecoEntity localizarUltimoEnderecoSalvo(List<EnderecoEntity> enderecos) {
//        int listSize = enderecos.size();
//        return enderecos.get(listSize - 1);
//    }
//
//    private PessoaEntity extrairPessoaDoEndereco(List<EnderecoEntity> enderecosAchados) {
//        return enderecosAchados.get(0).getPessoa();
//    }
//
    private List<EnderecoEntity> buscarPessoaOuFalhar(Long idPessoa) {
        List<EnderecoEntity> enderecosAchados = enderecoRepository.findByPessoaId(idPessoa);

        if (enderecosAchados.isEmpty()) {
            throw new PessoaNaoEncontradaException("Pessoa não encontrada para o id: " + idPessoa);
        }

        return enderecosAchados;
    }
//
//    private void converterEnderecoRequestEmEnderecoEntity(Set<EnderecoRequest> enderecosRequests, List<EnderecoEntity> enderecos) {
//        enderecosRequests.forEach(endereco -> enderecos.add(enderecoMapper.toEnderecoEntity(endereco)));
//    }
//
//    private List<EnderecoEntity> compararEnderecosIguais(List<EnderecoEntity> enderecos, List<EnderecoEntity> enderecosAchados) {
//        return enderecos.stream().filter(endereco -> !enderecosAchados.contains(endereco)).toList();
//    }
//
//    private EnderecoLoteResponse processarPersistenciaEnderecosEmLote(List<EnderecoEntity> enderecosElegiveis, PessoaEntity pessoaEntity) {
//        enderecosElegiveis.forEach(endereco -> {
//            endereco.setIsPrincipal(false);
//            endereco.setPessoa(pessoaEntity);
//        });
//        List<EnderecoEntity> enderecoEntities = enderecoRepository.saveAll(enderecosElegiveis);
//        List<EnderecoResponse> enderecos = enderecoMapper.toEnderecoLoteResponse(enderecoEntities);
//
//        enderecoLoteResponse.setEnderecos(enderecos);
//        return enderecoLoteResponse;
//    }
//
//    private EnderecoEntity buscarEnderecoOuFalhar(Long idEndereco) {
//        return enderecoRepository.findById(idEndereco)
//                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado para o id: " + idEndereco));
//    }

}

