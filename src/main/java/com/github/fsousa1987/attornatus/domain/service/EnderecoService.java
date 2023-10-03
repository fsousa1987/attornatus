package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.endereco.AtualizarEnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoLoteRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;

import java.util.Set;

public interface EnderecoService {

    EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest);

    EnderecoLoteResponse adicionarEnderecosEmLote(Long idPessoa, Set<EnderecoLoteRequest> enderecosRequests);

    EnderecoLoteResponse listarEnderecos(Long idPessoa);

    EnderecoResponse atualizarEndereco(Long idEndereco, AtualizarEnderecoRequest enderecoRequest);

    EnderecoResponse alterarPrincipal(Long idEndereco);
}
