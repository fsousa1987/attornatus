package com.github.fsousa1987.attornatus.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AtualizarPessoaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6460752133336888794L;

    @NotNull
    private String nome;

    @NotBlank
    private LocalDate dataNascimento;

}
