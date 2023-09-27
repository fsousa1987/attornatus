package com.github.fsousa1987.attornatus.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnderecoRequest {

    @NotNull
    private String logradouro;

    @NotNull
    private String cep;

    @NotBlank
    private int numero;

    @NotNull
    private String cidade;

    @NotNull
    private Boolean isPrincipal;

}
