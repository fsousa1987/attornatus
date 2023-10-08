package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.EnderecoJaCadastradoException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoLoteException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
import com.github.fsousa1987.attornatus.domain.service.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    EnderecoService service;

    @MockBean
    EnderecoRepository enderecoRepository;

    @MockBean
    ModelMapper enderecoMapper;

    EnderecoLoteResponse enderecoLoteResponse;

    @BeforeEach
    public void setUp() {
        enderecoLoteResponse = new EnderecoLoteResponse();
        this.service = new EnderecoServiceImpl(enderecoLoteResponse, enderecoRepository, enderecoMapper);
    }

    @Test
    @DisplayName("Deve adicionar endereço com sucesso")
    public void adicionarEnderecoComSucesso() {
        var enderecoRequest = createEnderecoRequest();
        var enderecoEntity = createEnderecoEntity();
        enderecoEntity.setCep("07097-041");
        var enderecoEntityForMapper = createEnderecoEntity();
        var enderecoResponse = createEnderecoResponse();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.map(enderecoRequest, EnderecoEntity.class)).thenReturn(enderecoEntityForMapper);
        when(enderecoRepository.save(any(EnderecoEntity.class))).thenReturn(enderecoEntity);
        when(enderecoMapper.map(enderecoEntityForMapper, EnderecoResponse.class)).thenReturn(enderecoResponse);

        var response = service.adicionarEndereco(1L, enderecoRequest);

        assertThat(response.getId()).isEqualTo(enderecoEntity.getId());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando não for encontrada uma pessoa")
    public void lancarExcecaoQuandoNaoEncontrarUmaPessoa() {
        var enderecoRequest = createEnderecoRequest();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of());

        assertThrows(PessoaNaoEncontradaException.class,
                () -> service.adicionarEndereco(1L, enderecoRequest));
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar salvar um endereço já cadastrado")
    public void lancarExcecaoQuandoCadastrarEnderecoExistente() {
        var enderecoRequest = createEnderecoRequest();
        var enderecoEntity = createEnderecoEntity();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.map(enderecoRequest, EnderecoEntity.class)).thenReturn(enderecoEntity);
        when(enderecoRepository.save(any(EnderecoEntity.class))).thenReturn(enderecoEntity);

        assertThrows(EnderecoJaCadastradoException.class,
                () -> service.adicionarEndereco(1L, enderecoRequest));
    }

    @Test
    @DisplayName("Deve salvar endereços em lote com sucesso")
    public void salvarEnderecosEmLoteSucesso() {
        var enderecoRequest = createEnderecoRequest();
        var enderecoEntity = createEnderecoEntity();
        enderecoEntity.setCep("07097-041");
        var enderecoEntityForMapper = createEnderecoEntity();
        var enderecoResponse = createEnderecoResponse();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.map(enderecoRequest, EnderecoEntity.class)).thenReturn(enderecoEntityForMapper);
        when(enderecoRepository.saveAll(anyList())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.map(enderecoEntity, EnderecoResponse.class)).thenReturn(enderecoResponse);

        enderecoLoteResponse = service.adicionarEnderecosEmLote(1L, Set.of(enderecoRequest));

        assertFalse(enderecoLoteResponse.getEnderecos().isEmpty());
        assertThat(enderecoLoteResponse.getEnderecos().get(0).getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o lote está inválido")
    public void lancarExcecaoQuandoOEnderecoEmLoteEstaInvalido() {
        var enderecoRequest = createEnderecoRequest();
        var enderecoEntity = createEnderecoEntity();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.map(enderecoRequest, EnderecoEntity.class)).thenReturn(enderecoEntity);

        assertThrows(InvalidEnderecoLoteException.class,
                () -> service.adicionarEnderecosEmLote(1L, Set.of(enderecoRequest)));
    }

    @Test
    @DisplayName("Deve listar endereços com sucesso")
    public void listarEnderecos() {
        var enderecoRequest = createEnderecoRequest();
        var enderecoEntity = createEnderecoEntity();
        EnderecoResponse enderecoResponse = createEnderecoResponse();

        when(enderecoRepository.findByPessoaId(anyLong())).thenReturn(List.of(enderecoEntity));
        when(enderecoMapper.map(enderecoEntity, EnderecoResponse.class)).thenReturn(enderecoResponse);

        enderecoLoteResponse = service.listarEnderecos(1L);

        assertFalse(enderecoLoteResponse.getEnderecos().isEmpty());
        assertThat(enderecoLoteResponse.getEnderecos().get(0).getId()).isNotNull();
    }

}
