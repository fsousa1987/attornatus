package com.github.fsousa1987.attornatus.factory;

import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecosLoteRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.api.request.pessoa.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponseList;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Factory {

    private Factory() {
    }

    public static EnderecoResponse createEnderecoResponse() {
        return EnderecoResponse
                .builder()
                .cep("73010-121")
                .cidade("Brasília")
                .numero(7)
                .isPrincipal(true)
                .logradouro("Quadra 12 Conjunto A")
                .id(1L)
                .build();
    }

    public static PessoaResponse createPessoaResponse() {
        EnderecoResponse enderecoResponse = createEnderecoResponse();

        return PessoaResponse
                .builder()
                .id(1L)
                .dataNascimento(LocalDate.now().minusYears(25))
                .nome("Francisco Sousa")
                .enderecos(List.of(enderecoResponse))
                .build();
    }

    public static AdicionarEnderecoRequest createAdicionarEnderecoRequest() {
        return AdicionarEnderecoRequest
                .builder()
                .cidade("Brasilia")
                .numero(6)
                .cep("73010-121")
                .isPrincipal(true)
                .logradouro("Quadra 12 Conjunto A")
                .build();
    }

    public static SalvarPessoaRequest createSalvarPessoaRequest() {
        var adicionarEnderecoRequest = createAdicionarEnderecoRequest();

        return SalvarPessoaRequest
                .builder()
                .nome("Francisco Sousa")
                .dataNascimento(LocalDate.now().minusYears(25))
                .enderecos(List.of(adicionarEnderecoRequest))
                .build();
    }

    public static PessoaResponseList createPessoaResponseList() {
        var pessoaResponse = createPessoaResponse();

        return PessoaResponseList.builder()
                .pessoas(List.of(pessoaResponse))
                .build();
    }

    public static EnderecoLoteResponse createEnderecoLoteResponse() {
        var enderecoResponse = createEnderecoResponse();

        return EnderecoLoteResponse
                .builder()
                .enderecos(List.of(enderecoResponse))
                .build();
    }

    public static AdicionarEnderecosLoteRequest createAdicionarEnderecosLoteRequest() {
        EnderecoRequest enderecoRequest = createEnderecoRequest();

        return AdicionarEnderecosLoteRequest.builder()
                .enderecos(Set.of(enderecoRequest))
                .build();
    }

    public static EnderecoRequest createEnderecoRequest() {
        return EnderecoRequest.builder()
                .numero(95)
                .cep("73010-121")
                .cidade("Guarulhos")
                .logradouro("Rua Thomas Biondillo")
                .build();
    }

    public static PessoaEntity createPessoaEntity() {
        EnderecoEntity enderecoEntity = createEnderecoEntity();

        return PessoaEntity
                .builder()
                .id(1L)
                .nome("Francisco Sousa")
                .dataNascimento(LocalDate.now().minusYears(25))
                .enderecos(List.of(enderecoEntity))
                .build();
    }

    public static EnderecoEntity createEnderecoEntity() {
        return EnderecoEntity
                .builder()
                .numero(95)
                .cep("73010-121")
                .cidade("Guarulhos")
                .logradouro("Rua Thomas Biondillo")
                .isPrincipal(true)
                .build();
    }

    public static PessoaRequest createPessoaRequest() {
        return PessoaRequest
                .builder()
                .nome("Francisco Sousa")
                .dataNascimento(LocalDate.now().minusYears(25))
                .build();
    }

}