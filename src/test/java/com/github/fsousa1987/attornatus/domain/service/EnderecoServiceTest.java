package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
import com.github.fsousa1987.attornatus.core.mapper.PessoaMapper;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    EnderecoService service;

    @MockBean
    PessoaRepository pessoaRepository;

    @MockBean
    EnderecoRepository enderecoRepository;

    @MockBean
    PessoaMapper pessoaMapper;

    @MockBean
    EnderecoMapper enderecoMapper;

    @BeforeEach
    public void setUp() {
        this.service = new EnderecoService(pessoaRepository, enderecoMapper, enderecoRepository);
    }

    @Test
    @DisplayName("Deve adicionar endereço com sucesso")
    public void adicionarEnderecoComSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        EnderecoEntity enderecoEntity = createEnderecoEntity();
        EnderecoResponse enderecoResponse = createEnderecoResponse();
        EnderecoRequest enderecoRequest = createEnderecoRequest();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
        when(enderecoMapper.toEnderecoEntity(Mockito.any(EnderecoRequest.class))).thenReturn(enderecoEntity);
        when(enderecoRepository.save(any(EnderecoEntity.class))).thenReturn(enderecoEntity);
        when(pessoaRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntity);
        when(enderecoMapper.toEnderecoResponse(any(EnderecoEntity.class))).thenReturn(enderecoResponse);

        EnderecoResponse response = service.adicionarEndereco(1L, enderecoRequest);

        assertThat(response.getId()).isEqualTo(enderecoEntity.getId());
        verify(pessoaRepository, atLeastOnce()).findById(anyLong());
        verify(enderecoRepository, atLeastOnce()).save(any(EnderecoEntity.class));
        verify(pessoaRepository, atLeastOnce()).save(any(PessoaEntity.class));
    }

    @Test
    @DisplayName("Deve listar todos os endereços de uma pessoa")
    public void listarTodosEnderecosDeUmaPessoaSucesso() {
        PessoaEntity pessoaEntity = createPessoaEntity();
        EnderecoResponse enderecoResponse = createEnderecoResponse();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
        when(enderecoMapper.toListEnderecoResponse(anyList())).thenReturn(List.of(enderecoResponse));

        List<EnderecoResponse> enderecoResponses = service.listarEnderecos(1L);

        assertFalse(enderecoResponses.isEmpty());
        verify(pessoaRepository, atLeastOnce()).findById(anyLong());
    }

}
