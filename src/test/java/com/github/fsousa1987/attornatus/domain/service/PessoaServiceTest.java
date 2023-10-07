//package com.github.fsousa1987.attornatus.domain.service;
//
//import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.SemEnderecoPrincipalException;
//import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
//import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponse;
//import com.github.fsousa1987.attornatus.core.mapper.EnderecoMapper;
//import com.github.fsousa1987.attornatus.core.mapper.PessoaMapper;
//import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
//import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
//import com.github.fsousa1987.attornatus.domain.repository.EnderecoRepository;
//import com.github.fsousa1987.attornatus.domain.repository.PessoaRepository;
//import com.github.fsousa1987.attornatus.domain.service.impl.PessoaServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.github.fsousa1987.attornatus.factory.Factory.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//public class PessoaServiceTest {
//
//    PessoaService service;
//
//    @MockBean
//    PessoaRepository pessoaRepository;
//
//    @MockBean
//    EnderecoRepository enderecoRepository;
//
//    @MockBean
//    PessoaMapper pessoaMapper;
//
//    @MockBean
//    EnderecoMapper enderecoMapper;
//
//    @BeforeEach
//    public void setUp() {
//        this.service = new PessoaServiceImpl(pessoaRepository, enderecoRepository, pessoaMapper, enderecoMapper);
//    }
//
//    @Test
//    @DisplayName("Deve salvar uma pessoa com sucesso")
//    public void salvarUmaPessoaComSucesso() {
//        PessoaEntity pessoaEntity = createPessoaEntity();
//        EnderecoEntity enderecoEntity = createEnderecoEntity();
//        PessoaResponse pessoaResponse = createPessoaResponse();
//
//        when(pessoaMapper.toPessoaEntity(any(PessoaRequest.class))).thenReturn(pessoaEntity);
//        when(pessoaRepository.save(any(PessoaEntity.class))).thenReturn(createPessoaEntity());
//        when(enderecoRepository.saveAll(anyList())).thenReturn(List.of(enderecoEntity));
//        when(pessoaMapper.toPessoaResponse(any(PessoaEntity.class))).thenReturn(pessoaResponse);
//
//        PessoaResponse response = service.salvarPessoa(createSalvarPessoaRequest());
//
//        assertThat(response.getId()).isEqualTo(pessoaResponse.getId());
//        assertThat(response.getNome()).isEqualTo(pessoaResponse.getNome());
//        assertThat(response.getDataNascimento()).isEqualTo(pessoaResponse.getDataNascimento());
//        assertFalse(response.getEnderecos().isEmpty());
//
//        verify(pessoaRepository, atLeastOnce()).save(any(PessoaEntity.class));
//    }
//
//    @Test
//    @DisplayName("Deve lançar uma exceção ao tentar salvar uma pessoa sem um endereço principal")
//    public void deveLancarUmaExcecaoQuandoAPessoaNaoTemEnderecoPrincipal() {
//        PessoaRequest salvarPessoaRequest = createSalvarPessoaRequest();
//        salvarPessoaRequest.listaDeEnderecos().get(0).setIsPrincipal(false);
//
//        assertThrows(SemEnderecoPrincipalException.class,
//                () -> service.salvarPessoa(salvarPessoaRequest));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar uma pessoa com sucesso")
//    public void atualizarUmaPessoaComSucesso() {
//        PessoaEntity pessoaEntity = createPessoaEntity();
//        PessoaRequest atualizarPessoaRequest = createAtualizarPessoaRequest();
//
//        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
//        when(pessoaRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntity);
//
//        service.atualizarPessoa(1L, atualizarPessoaRequest);
//
//        verify(pessoaRepository, atLeastOnce()).findById(anyLong());
//        verify(pessoaRepository, atLeastOnce()).save(any(PessoaEntity.class));
//    }
//
//    @Test
//    @DisplayName("Deve buscar pessoa pelo id com sucesso")
//    public void buscarPessoaPorIdComSucesso() {
//        PessoaEntity pessoaEntity = createPessoaEntity();
//        PessoaResponse pessoaResponse = createPessoaResponse();
//
//        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaEntity));
//        when(pessoaMapper.toPessoaResponse(any(PessoaEntity.class))).thenReturn(pessoaResponse);
//
//        PessoaResponse response = service.buscarPorId(1L);
//
//        assertThat(response.getId()).isEqualTo(pessoaEntity.getId());
//        verify(pessoaRepository, atLeastOnce()).findById(anyLong());
//    }
//
//    @Test
//    @DisplayName("Deve buscar todas as pessoas com sucesso")
//    public void buscarTodasPessoasComSucesso() {
//        PessoaEntity pessoaEntity = createPessoaEntity();
//        PessoaResponse pessoaResponse = createPessoaResponse();
//
//        when(pessoaRepository.findAll()).thenReturn(List.of(pessoaEntity));
//        when(pessoaMapper.toListPessoaResponse(anyList())).thenReturn(List.of(pessoaResponse));
//
//        List<PessoaResponse> pessoaResponses = service.buscarTodas();
//
//        verify(pessoaRepository, atLeastOnce()).findAll();
//        assertFalse(pessoaResponses.isEmpty());
//    }
//
//}
