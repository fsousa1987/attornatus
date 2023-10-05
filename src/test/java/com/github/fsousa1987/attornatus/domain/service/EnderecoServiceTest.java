package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.service.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    EnderecoService service;

    @MockBean
    EnderecoRepository enderecoRepository;

    @MockBean
    EnderecoLoteResponse enderecoLoteResponse;

    @MockBean
    EnderecoMapper enderecoMapper;

    @BeforeEach
    public void setUp() {
        this.service = new EnderecoServiceImpl(enderecoLoteResponse, enderecoRepository, enderecoMapper);
    }

    @Test
    @DisplayName("Deve adicionar endereço com sucesso")
    public void adicionarEnderecoComSucesso() {
        EnderecoEntity enderecoEntity = createEnderecoEntity();
        EnderecoResponse enderecoResponse = createEnderecoResponse();
        EnderecoRequest enderecoRequest = createEnderecoRequest();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(new ArrayList<>(List.of(enderecoEntity)));
        when(enderecoMapper.toEnderecoEntity(any(EnderecoRequest.class))).thenReturn(enderecoEntity);
        when(enderecoRepository.saveAll(anyList())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.toEnderecoResponse(any(EnderecoEntity.class))).thenReturn(enderecoResponse);

        EnderecoResponse response = service.adicionarEndereco(1L, enderecoRequest);

        assertThat(response.getId()).isEqualTo(enderecoEntity.getId());
        verify(enderecoRepository, atLeastOnce()).findByPessoaId(anyLong());
        verify(enderecoRepository, atLeastOnce()).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve listar todos os endereços de uma pessoa")
    public void listarTodosEnderecosDeUmaPessoaSucesso() {
        EnderecoEntity enderecoEntity = createEnderecoEntity();
        EnderecoResponse enderecoResponse = createEnderecoResponse();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.toListEnderecoResponse(anyList())).thenReturn(List.of(enderecoResponse));
        when(enderecoLoteResponse.getEnderecos()).thenReturn(List.of(enderecoResponse));

        enderecoLoteResponse = service.listarEnderecos(1L);

        assertFalse(enderecoLoteResponse.getEnderecos().isEmpty());
        verify(enderecoRepository, atLeastOnce()).findByPessoaId(anyLong());
    }

    @Test
    @DisplayName("Deve levantar uma exceção quando não encontrar uma pessoa")
    public void levantarUmaExcecaoQuandoNaoForEncontradaUmaPessoa() {
        EnderecoRequest enderecoRequest = createEnderecoRequest();

        assertThrows(PessoaNaoEncontradaException.class,
                () -> service.adicionarEndereco(1L, enderecoRequest));
    }

}
