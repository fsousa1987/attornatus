package com.github.fsousa1987.attornatus.api.request.endereco;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdicionarEnderecoRequest extends EnderecoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2470936626242499104L;

    @NotNull(message = "preencha se o endereço é principal")
    private Boolean isPrincipal;

    @Override
    public boolean isEnderecoPrincipal() {
        return isPrincipal;
    }
}
