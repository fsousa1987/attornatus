package com.github.fsousa1987.attornatus.api.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PessoaResponse {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private List<EnderecoResponse> enderecos;

}
