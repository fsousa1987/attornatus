package com.github.fsousa1987.attornatus.api.response.endereco;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class EnderecoLoteResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 8189080932897822415L;

    List<EnderecoResponse> enderecos;

}
