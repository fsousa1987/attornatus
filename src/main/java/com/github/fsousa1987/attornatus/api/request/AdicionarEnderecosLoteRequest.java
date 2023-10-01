package com.github.fsousa1987.attornatus.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdicionarEnderecosLoteRequest extends EnderecoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6674561873873253800L;

    @NotNull(message = "preencha se o endereço é principal")
    @JsonIgnore
    private Boolean isPrincipal;

}
