package com.github.fsousa1987.attornatus.api.request.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class EnderecoLoteRequest extends EnderecoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8780241178200772086L;

    @JsonIgnore
    private Boolean isPrincipal;

}
