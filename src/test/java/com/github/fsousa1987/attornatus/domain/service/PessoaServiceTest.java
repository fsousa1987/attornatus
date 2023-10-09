package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.SemEnderecoPrincipalException;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponseList;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import com.github.fsousa1987.attornatus.domain.service.impl.PessoaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

    PessoaService service;

    @MockBean
    PessoaRepository pessoaRepository;

    @MockBean
    EnderecoRepository enderecoRepository;

    @MockBean
    ModelMapper mapper;

    @BeforeEach
    public void setUp() {
        this.service = new PessoaServiceImpl(pessoaRepository, enderecoRepository, mapper);
    }

    @Test
    @DisplayName("Deve salvar uma pessoa com sucesso")
    public void salvarUmaPessoaComSucesso() {
        var salvarPessoaRequest = createSalvarPessoaRequest();
        var pessoaEntity = createPessoaEntity();
        var pessoaResponse = createPessoaResponse();

        when(pessoaRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntity);
        when(enderecoRepository.saveAll(anyList())).thenReturn(pessoaEntity.getEnderecos());
        when(mapper.map(salvarPessoaRequest, PessoaEntity.class)).thenReturn(pessoaEntity);
        when(mapper.map(pessoaEntity, PessoaResponse.class)).thenReturn(pessoaResponse);

        pessoaResponse = service.salvarPessoa(salvarPessoaRequest);

        assertThat(pessoaResponse.getId()).isEqualTo(pessoaEntity.getId());
        assertFalse(pessoaResponse.getEnderecos().isEmpty());
        verify(pessoaRepository, atLeastOnce()).save(pessoaEntity);
        verify(enderecoRepository, atLeastOnce()).saveAll(pessoaEntity.getEnderecos());

    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar salvar uma pessoa sem um endereço principal")
    public void deveLancarUmaExcecaoQuandoAPessoaNaoTemEnderecoPrincipal() {
        var salvarPessoaRequest = createSalvarPessoaRequest();
        salvarPessoaRequest.listaDeEnderecos().get(0).setPrincipal(false);

        assertThrows(SemEnderecoPrincipalException.class,
                () -> service.salvarPessoa(salvarPessoaRequest));
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa com sucesso")
    public void atualizarUmaPessoaComSucesso() {
        var pessoaEntity = createPessoaEntity();
        var pessoaRequest = createPessoaRequest();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
        when(pessoaRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntity);

        service.atualizarPessoa(1L, pessoaRequest);

        verify(pessoaRepository, atLeastOnce()).findById(anyLong());
        verify(pessoaRepository, atLeastOnce()).save(any(PessoaEntity.class));
    }

    @Test
    @DisplayName("Deve buscar pessoa pelo id com sucesso")
    public void buscarPessoaPorIdComSucesso() {
        var pessoaEntity = createPessoaEntity();
        var pessoaResponse = createPessoaResponse();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
        when(mapper.map(pessoaEntity, PessoaResponse.class)).thenReturn(pessoaResponse);

        pessoaResponse = service.buscarPorId(1L);

        assertThat(pessoaResponse.getId()).isEqualTo(pessoaEntity.getId());
        verify(pessoaRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas com sucesso")
    public void buscarTodasPessoasComSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        PessoaResponse pessoaResponse = createPessoaResponse();

        when(pessoaRepository.findAll()).thenReturn(List.of(pessoaEntity));
        when(mapper.map(pessoaEntity, PessoaResponse.class)).thenReturn(pessoaResponse);

        PessoaResponseList pessoaResponseList = service.buscarTodas();

        verify(pessoaRepository, atLeastOnce()).findAll();
        assertFalse(pessoaResponseList.getPessoas().isEmpty());
    }

}
