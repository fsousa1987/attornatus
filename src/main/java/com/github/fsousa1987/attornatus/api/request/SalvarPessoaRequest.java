package com.github.fsousa1987.attornatus.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SalvarPessoaRequest {

    @NotNull
    private String nome;

    @NotBlank
    private LocalDate dataNascimento;

    @NotBlank
    private List<EnderecoRequest> enderecos;

}
