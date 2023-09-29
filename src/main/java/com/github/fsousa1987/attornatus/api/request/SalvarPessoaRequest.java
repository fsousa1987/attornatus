package com.github.fsousa1987.attornatus.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SalvarPessoaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8826148077458704971L;

    @NotNull
    private String nome;

    @NotBlank
    private LocalDate dataNascimento;

    @NotBlank
    private List<EnderecoRequest> enderecos;

}
