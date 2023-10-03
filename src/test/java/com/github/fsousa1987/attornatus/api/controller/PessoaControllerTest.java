package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.pessoa.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.domain.service.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.fsousa1987.attornatus.factory.Factory.createPessoaResponse;
import static com.github.fsousa1987.attornatus.factory.Factory.createSalvarPessoaRequest;
import static com.github.fsousa1987.attornatus.util.JsonResponse.asJsonString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    static String PESSOA_URI = "/api/v1/pessoas";

    @Autowired
    MockMvc mvc;

    @MockBean
    PessoaService service;

    @Test
    @DisplayName("Deve salvar uma pessoa com sucesso")
    public void salvarUmaPessoaComSucesso() throws Exception {

        given(service.salvarPessoa(any(SalvarPessoaRequest.class))).willReturn(createPessoaResponse());

        var request = MockMvcRequestBuilders
                .post(PESSOA_URI)
                .content(asJsonString(createSalvarPessoaRequest()))
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
        given(service.buscarPorId(anyLong())).willReturn(createPessoaResponse());

        var request = MockMvcRequestBuilders
                .get(PESSOA_URI.concat("/1"))
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve buscar todas as pessoas com sucesso")
    public void buscarTodasAsPessoaComSucesso() throws Exception {
        given(service.buscarTodas()).willReturn(List.of(createPessoaResponse()));

        var request = MockMvcRequestBuilders
                .get(PESSOA_URI)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
    }

}
