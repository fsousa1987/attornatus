package com.github.fsousa1987.attornatus.api.response.pessoa;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PessoaResponseList implements Serializable {

    @Serial
    private static final long serialVersionUID = 4827929669793594796L;

    private List<PessoaResponse> pessoas;

}
