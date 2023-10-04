package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;

import java.util.List;

public interface PessoaService {

    PessoaResponse salvarPessoa(PessoaRequest salvarPessoaRequest);

    void atualizarPessoa(Long id, PessoaRequest atualizarPessoaRequest);

    PessoaResponse buscarPorId(Long id);

    List<PessoaResponse> buscarTodas();

}
