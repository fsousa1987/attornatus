package com.github.fsousa1987.attornatus.core.mapper;

import com.github.fsousa1987.attornatus.api.request.pessoa.PessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaEntity toPessoaEntity(PessoaRequest salvarPessoaRequest);

    PessoaResponse toPessoaResponse(PessoaEntity pessoaEntity);

    List<PessoaResponse> toListPessoaResponse(List<PessoaEntity> pessoaEntityList);

}
