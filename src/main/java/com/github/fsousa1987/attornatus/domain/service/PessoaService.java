package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.AtualizarPessoaRequest;
import com.github.fsousa1987.attornatus.api.request.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;

import java.util.List;

public interface PessoaService {

    PessoaResponse salvarPessoa(SalvarPessoaRequest salvarPessoaRequest);

    void atualizarPessoa(Long id, AtualizarPessoaRequest atualizarPessoaRequest);

    PessoaResponse buscarPorId(Long id);

    List<PessoaResponse> buscarTodas();

}
