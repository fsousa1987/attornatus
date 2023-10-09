package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.domain.service.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.fsousa1987.attornatus.factory.Factory.*;
import static com.github.fsousa1987.attornatus.util.JsonResponse.asJsonString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    static final String PESSOA_URI = "/api/v1/pessoas";

    @Autowired
    MockMvc mvc;

    @MockBean
    PessoaService service;

    @Test
    @DisplayName("Deve salvar uma pessoa com sucesso")
    public void salvarUmaPessoaComSucesso() throws Exception {
        var pessoaResponse = createPessoaResponse();
        var salvarPessoaRequest = createSalvarPessoaRequest();

        given(service.salvarPessoa(any(PessoaRequest.class))).willReturn(pessoaResponse);

        var request = MockMvcRequestBuilders
                .post(PESSOA_URI)
                .content(asJsonString(salvarPessoaRequest))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa com sucesso")
    public void atualizarUmaPessoaComSucesso() throws Exception {
        var request = MockMvcRequestBuilders
                .put(PESSOA_URI.concat("/1"))
                .content(asJsonString(createSalvarPessoaRequest()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve buscar uma pessoa com sucesso")
    public void buscarUmaPessoaComSucesso() throws Exception {
        var pessoaResponse = createPessoaResponse();

        given(service.buscarPorId(anyLong())).willReturn(pessoaResponse);

        var request = MockMvcRequestBuilders
                .get(PESSOA_URI.concat("/1"))
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas com sucesso")
    public void buscarTodasAsPessoaComSucesso() throws Exception {
        var pessoaResponseList = createPessoaResponseList();

        given(service.buscarTodas()).willReturn(pessoaResponseList);

        var request = MockMvcRequestBuilders
                .get(PESSOA_URI)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
    }

}
