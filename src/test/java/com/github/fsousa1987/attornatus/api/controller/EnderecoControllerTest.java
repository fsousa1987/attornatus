package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.domain.service.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static com.github.fsousa1987.attornatus.util.JsonResponse.asJsonString;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTest {

    static String ENDERECO_URI = "/api/v1/enderecos";

    @Autowired
    MockMvc mvc;

    @MockBean
    EnderecoServiceImpl service;

    @Test
    @DisplayName("Deve adicionar um endereço para a pessoa com sucesso")
    public void adicionarEnderecoParaPessoa() throws Exception {
        var enderecoResponse = createEnderecoResponse();
        var adicionarEnderecoRequest = createAdicionarEnderecoRequest();

        given(service.adicionarEndereco(anyLong(), any(EnderecoRequest.class))).willReturn(enderecoResponse);

        var request = MockMvcRequestBuilders
                .post(ENDERECO_URI.concat("/pessoas/1/inclusao"))
                .content(asJsonString(adicionarEnderecoRequest))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve adicionar endereços em lote com sucesso")
    public void adicionarEnderecosEmLote() throws Exception {
        var enderecoLoteResponse = createEnderecoLoteResponse();
        var adicionarEnderecosLoteRequest = createAdicionarEnderecosLoteRequest();

        given(service.adicionarEnderecosEmLote(anyLong(), anySet())).willReturn(enderecoLoteResponse);

        var request = MockMvcRequestBuilders
                .post(ENDERECO_URI.concat("/pessoas/1/inclusao/lote"))
                .content(asJsonString(adicionarEnderecosLoteRequest))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.enderecos[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve recuperar todos os endereços de uma pessoa")
    public void recuperarTodosEnderecosPessoa() throws Exception {
        var enderecoLoteResponse = createEnderecoLoteResponse();

        given(service.listarEnderecos(anyLong())).willReturn(enderecoLoteResponse);

        var request = MockMvcRequestBuilders
                .get(ENDERECO_URI.concat("/pessoas/1"))
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar um endereço com sucesso")
    public void atualizarEndereco() throws Exception {
        var enderecoResponse = createEnderecoResponse();
        var enderecoRequest = createEnderecoRequest();

        given(service.atualizarEndereco(anyLong(), any(EnderecoRequest.class))).willReturn(enderecoResponse);

        var request = MockMvcRequestBuilders
                .put(ENDERECO_URI.concat("/1"))
                .content(asJsonString(enderecoRequest))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve alterar o endereço principal com sucesso")
    public void alterarEnderecoPrincipal() throws Exception {
        var enderecoResponse = createEnderecoResponse();

        given(service.alterarPrincipal(anyLong())).willReturn(enderecoResponse);

        var request = MockMvcRequestBuilders
                .put(ENDERECO_URI.concat("/1/alteracao/principal"))
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

}
