package com.github.fsousa1987.attornatus.domain.service;

import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;

import java.util.List;

public interface EnderecoService {

    EnderecoResponse adicionarEndereco(Long idPessoa, EnderecoRequest enderecoRequest);

    List<EnderecoResponse> listarEnderecos(Long idPessoa);

}
