package com.github.fsousa1987.attornatus.api.response;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PessoaResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5062850503161871904L;

    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private List<EnderecoResponse> enderecos;

}
