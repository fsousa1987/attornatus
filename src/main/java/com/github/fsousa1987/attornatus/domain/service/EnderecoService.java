package com.github.fsousa1987.attornatus.domain.service;

//import com.github.fsousa1987.attornatus.api.request.endereco.AtualizarEnderecoRequest;

import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;

import java.util.Set;

public interface EnderecoService {

    EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest);

    EnderecoLoteResponse adicionarEnderecosEmLote(Long idPessoa, Set<EnderecoRequest> enderecos);

    EnderecoLoteResponse listarEnderecos(Long idPessoa);

    EnderecoResponse atualizarEndereco(Long idEndereco, EnderecoRequest enderecoRequest);

    EnderecoResponse alterarPrincipal(Long idEndereco);
}
