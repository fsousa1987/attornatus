package com.github.fsousa1987.attornatus.api.request.endereco;

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
public class AtualizarEnderecoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 726434866200169290L;

    @NotBlank(message = "logradouro não pode estar em branco")
    private String logradouro;

    @NotBlank(message = "cep não pode estar em branco")
    private String cep;

    @NotNull(message = "numero nao pode estar em branco")
    private Integer numero;

    @NotBlank(message = "cidade nao pode estar em branco")
    private String cidade;

}
