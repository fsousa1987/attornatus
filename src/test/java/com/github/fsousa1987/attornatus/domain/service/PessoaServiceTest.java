package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.AtualizarPessoaRequest;
import com.github.fsousa1987.attornatus.api.request.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.core.mapper.PessoaMapper;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import com.github.fsousa1987.attornatus.domain.service.impl.PessoaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

    PessoaService service;

    @MockBean
    PessoaRepository repository;

    @MockBean
    PessoaMapper mapper;

    @BeforeEach
    public void setUp() {
        this.service = new PessoaServiceImpl(repository, mapper);
    }

    @Test
    @DisplayName("Deve salvar uma pessoa com sucesso")
    public void salvarUmaPessoaComSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        PessoaResponse pessoaResponse = createPessoaResponse();

        when(mapper.toPessoaEntity(any(SalvarPessoaRequest.class))).thenReturn(pessoaEntity);
        when(mapper.toPessoaResponse(any(PessoaEntity.class))).thenReturn(pessoaResponse);
        when(repository.save(any(PessoaEntity.class))).thenReturn(createPessoaEntity());

        PessoaResponse response = service.salvarPessoa(createSalvarPessoaRequest());

        assertThat(response.getId()).isEqualTo(pessoaResponse.getId());
        verify(repository, atLeastOnce()).save(any(PessoaEntity.class));
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa com sucesso")
    public void atualizarUmaPessoaComSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        AtualizarPessoaRequest atualizarPessoaRequest = createAtualizarPessoaRequest();

        when(repository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
        when(repository.save(any(PessoaEntity.class))).thenReturn(pessoaEntity);

        service.atualizarPessoa(1L, atualizarPessoaRequest);

        verify(repository, atLeastOnce()).findById(anyLong());
        verify(repository, atLeastOnce()).save(any(PessoaEntity.class));
    }

    @Test
    @DisplayName("Deve buscar pessoa pelo id com sucesso")
    public void buscarPessoaPorIdComSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        PessoaResponse pessoaResponse = createPessoaResponse();

        when(repository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
        when(mapper.toPessoaResponse(any(PessoaEntity.class))).thenReturn(pessoaResponse);

        PessoaResponse response = service.buscarPorId(1L);

        assertThat(response.getId()).isEqualTo(pessoaEntity.getId());
        verify(repository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas com sucesso")
    public void buscarTodasPessoasComSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        PessoaResponse pessoaResponse = createPessoaResponse();

        when(repository.findAll()).thenReturn(List.of(pessoaEntity));
        when(mapper.toListPessoaResponse(anyList())).thenReturn(List.of(pessoaResponse));

        List<PessoaResponse> pessoaResponses = service.buscarTodas();

        verify(repository, atLeastOnce()).findAll();
        assertFalse(pessoaResponses.isEmpty());
    }

}
