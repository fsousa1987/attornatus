package com.github.fsousa1987.attornatus.api.request.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EnderecoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -9158075475562277305L;

    @NotBlank(message = "logradouro não pode estar em branco")
    private String logradouro;

    @NotBlank(message = "cep não pode estar em branco")
    private String cep;

    @NotNull(message = "numero nao pode estar em branco")
    private Integer numero;

    @NotBlank(message = "cidade nao pode estar em branco")
    private String cidade;

    public boolean isEnderecoPrincipal() {
        return false;
    }
}
