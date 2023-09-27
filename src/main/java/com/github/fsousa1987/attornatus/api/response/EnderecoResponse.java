package com.github.fsousa1987.attornatus.api.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnderecoResponse {

    private Long id;
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
    private Boolean isPrincipal;

}
