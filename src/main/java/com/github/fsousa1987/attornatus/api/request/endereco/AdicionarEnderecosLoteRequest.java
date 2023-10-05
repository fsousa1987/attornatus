package com.github.fsousa1987.attornatus.api.request.endereco;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AdicionarEnderecosLoteRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6674561873873253800L;

    Set<EnderecoRequest> enderecos;

}
