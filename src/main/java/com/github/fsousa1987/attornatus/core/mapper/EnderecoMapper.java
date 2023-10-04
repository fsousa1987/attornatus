package com.github.fsousa1987.attornatus.core.mapper;

import com.github.fsousa1987.attornatus.api.request.endereco.AtualizarEnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoEntity toEnderecoEntity(EnderecoRequest enderecoRequest);

    List<EnderecoEntity> toListEnderecoEntity(List<EnderecoRequest> enderecos);

    EnderecoResponse toEnderecoResponse(EnderecoEntity enderecoEntity);

    List<EnderecoResponse> toListEnderecoResponse(List<EnderecoEntity> enderecoEntityList);

    List<EnderecoResponse> toEnderecoLoteResponse(List<EnderecoEntity> enderecos);

    EnderecoEntity toEnderecoEntity(AtualizarEnderecoRequest request);

}
