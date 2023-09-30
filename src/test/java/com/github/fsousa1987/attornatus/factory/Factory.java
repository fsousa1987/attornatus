package com.github.fsousa1987.attornatus.factory;

import com.github.fsousa1987.attornatus.api.request.AtualizarPessoaRequest;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factory {

    public static PessoaResponse createPessoaResponse() {
        return PessoaResponse
                .builder()
                .id(1L)
                .dataNascimento(LocalDate.now().minusYears(23))
                .nome("Francisco Sousa")
                .enderecos(List.of(createEnderecoResponse()))
                .build();
    }

    public static EnderecoResponse createEnderecoResponse() {
        return EnderecoResponse
                .builder()
                .cep("73010-121")
                .cidade("Guarulhos")
                .id(1L)
                .isPrincipal(true)
                .logradouro("Rua das Amélias")
                .numero(99)
                .build();
    }

    public static SalvarPessoaRequest createSalvarPessoaRequest() {
        return SalvarPessoaRequest
                .builder()
                .nome("Francisco Sousa")
                .dataNascimento(LocalDate.now().minusYears(23).toString())
                .enderecos(List.of(createEnderecoRequest()))
                .build();
    }

    public static EnderecoRequest createEnderecoRequest() {
        return EnderecoRequest
                .builder()
                .cep("73010-121")
                .cidade("Guarulhos")
                .isPrincipal(true)
                .logradouro("Rua das Amélias")
                .numero(99)
                .build();
    }

    public static PessoaEntity createPessoaEntity() {
        return PessoaEntity.builder()
                .id(1L)
                .dataNascimento(LocalDate.now().minusYears(23))
                .nome("Francisco Sousa")
                .enderecos(new ArrayList<>(List.of(createEnderecoEntity())))
                .build();
    }

    public static EnderecoEntity createEnderecoEntity() {
        return EnderecoEntity
                .builder()
                .cep("73010-121")
                .cidade("Guarulhos")
                .numero(95)
                .isPrincipal(false)
                .id(1L)
                .logradouro("Rua das Flores")
                .build();
    }

    public static AtualizarPessoaRequest createAtualizarPessoaRequest() {
        return AtualizarPessoaRequest
                .builder()
                .nome("Francisco Sousa")
                .dataNascimento(LocalDate.now().minusYears(23).toString())
                .build();
    }

}
