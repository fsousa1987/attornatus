package com.github.fsousa1987.attornatus.factory;

import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.pessoa.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponseList;

import java.time.LocalDate;
import java.util.List;

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
        AdicionarEnderecoRequest adicionarEnderecoRequest = createAdicionarEnderecoRequest();

        return SalvarPessoaRequest
                .builder()
                .nome("Francisco Sousa")
                .dataNascimento(LocalDate.now().minusYears(25))
                .enderecos(List.of(adicionarEnderecoRequest))
                .build();
    }

    public static PessoaResponseList createPessoaResponseList() {
        PessoaResponse pessoaResponse = createPessoaResponse();
        return PessoaResponseList.builder()
                .pessoas(List.of(pessoaResponse))
                .build();
    }

}
//
//    public static PessoaResponse createPessoaResponse() {
//        return PessoaResponse
//                .builder()
//                .id(1L)
//                .dataNascimento(LocalDate.now().minusYears(23))
//                .nome("Francisco Sousa")
//                .enderecos(List.of(createEnderecoResponse()))
//                .build();
//    }
//
//    public static EnderecoResponse createEnderecoResponse() {
//        return EnderecoResponse
//                .builder()
//                .cep("73010-121")
//                .cidade("Guarulhos")
//                .id(1L)
//                .isPrincipal(true)
//                .logradouro("Rua das Amélias")
//                .numero(99)
//                .build();
//    }
//
//    public static PessoaRequest createSalvarPessoaRequest() {
//        return SalvarPessoaRequest
//                .builder()
//                .nome("Francisco Sousa")
//                .dataNascimento(LocalDate.now().minusYears(23).toString())
//                .enderecos(List.of(createEnderecoRequest()))
//                .build();
//    }
//
//    public static EnderecoRequest createEnderecoRequest() {
//        return EnderecoRequest
//                .builder()
//                .cep("73010-121")
//                .cidade("Guarulhos")
//                .isPrincipal(true)
//                .logradouro("Rua das Amélias")
//                .numero(99)
//                .build();
//    }
//
//    public static PessoaEntity createPessoaEntity() {
//        return PessoaEntity.builder()
//                .id(1L)
//                .dataNascimento(LocalDate.now().minusYears(23))
//                .nome("Francisco Sousa")
//                .enderecos(new ArrayList<>(List.of(createEnderecoEntity())))
//                .build();
//    }
//
//    public static EnderecoEntity createEnderecoEntity() {
//        return EnderecoEntity
//                .builder()
//                .cep("73010-121")
//                .cidade("Guarulhos")
//                .numero(95)
//                .isPrincipal(false)
//                .id(1L)
//                .logradouro("Rua das Flores")
//                .build();
//    }
//
//    public static PessoaRequest createAtualizarPessoaRequest() {
//        return PessoaRequest
//                .builder()
//                .nome("Francisco Sousa")
//                .dataNascimento(LocalDate.now().minusYears(23).toString())
//                .build();
//    }
//
//    public static EnderecoLoteResponse createEnderecoLoteResponse() {
//        return EnderecoLoteResponse.builder()
//                .enderecos(List.of(createEnderecoResponse()))
//                .build();
//    }
//
//}
