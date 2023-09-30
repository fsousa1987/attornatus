package com.github.fsousa1987.attornatus.api.request;

import com.github.fsousa1987.attornatus.core.validation.Date;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SalvarPessoaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8826148077458704971L;

    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @Date(message = "o campo data de nascimento está inválido")
    private String dataNascimento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<EnderecoRequest> enderecos;

}
