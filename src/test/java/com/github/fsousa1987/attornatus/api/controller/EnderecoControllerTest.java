package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
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
        EnderecoResponse enderecoResponse = createEnderecoResponse();

        given(service.adicionarEndereco(anyLong(), any(EnderecoRequest.class))).willReturn(enderecoResponse);

        var request = MockMvcRequestBuilders
                .post(ENDERECO_URI.concat("/pessoas/1/inclusao"))
                .content(asJsonString(createEnderecoRequest()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve recuperar todos os endereços de uma pessoa")
    public void recuperarTodosEnderecosPessoa() throws Exception {
        EnderecoLoteResponse enderecoLoteResponse = createEnderecoLoteResponse();
        given(service.listarEnderecos(anyLong())).willReturn(enderecoLoteResponse);

        var request = MockMvcRequestBuilders
                .get(ENDERECO_URI.concat("/pessoas/1"))
                .accept(APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk());
    }

}
