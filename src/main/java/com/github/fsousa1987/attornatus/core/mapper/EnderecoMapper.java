package com.github.fsousa1987.attornatus.core.mapper;

import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.entity.EnderecoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoEntity toEnderecoEntity(EnderecoRequest enderecoRequest);

    EnderecoResponse toEnderecoResponse(EnderecoEntity enderecoEntity);

    List<EnderecoResponse> toListEnderecoResponse(List<EnderecoEntity> enderecoEntityList);

    List<EnderecoResponse> toEnderecoLoteResponse(List<EnderecoEntity> enderecos);

}
