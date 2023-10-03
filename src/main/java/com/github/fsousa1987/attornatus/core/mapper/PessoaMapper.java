package com.github.fsousa1987.attornatus.core.mapper;

import com.github.fsousa1987.attornatus.api.request.pessoa.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.domain.entity.PessoaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaEntity toPessoaEntity(SalvarPessoaRequest salvarPessoaRequest);

    PessoaResponse toPessoaResponse(PessoaEntity pessoaEntity);

    List<PessoaResponse> toListPessoaResponse(List<PessoaEntity> pessoaEntityList);

}
