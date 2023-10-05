package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponse;
import com.github.fsousa1987.attornatus.api.response.pessoa.PessoaResponseList;

public interface PessoaService {

    PessoaResponse salvarPessoa(PessoaRequest salvarPessoaRequest);

    void atualizarPessoa(Long id, PessoaRequest atualizarPessoaRequest);

    PessoaResponse buscarPorId(Long id);

    PessoaResponseList buscarTodas();

}
