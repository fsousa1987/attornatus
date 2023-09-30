package com.github.fsousa1987.attornatus.api.request;

import com.github.fsousa1987.attornatus.core.validation.Date;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AtualizarPessoaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6460752133336888794L;

    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @NotBlank(message = "data de nascimento é um campo obrigatório")
    @Date(message = "o campo data de nascimento está inválido")
    private String dataNascimento;

}
