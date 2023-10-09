package com.github.fsousa1987.attornatus.api.response.endereco;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnderecoResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 3756715075394805466L;

    @EqualsAndHashCode.Include
    private Long id;
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
    private boolean isPrincipal;

}
