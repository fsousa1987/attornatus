package com.github.fsousa1987.attornatus.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EnderecoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -9158075475562277305L;

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
