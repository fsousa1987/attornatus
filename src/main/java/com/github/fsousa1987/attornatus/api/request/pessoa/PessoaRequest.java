package com.github.fsousa1987.attornatus.api.request.pessoa;

import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.core.validation.Date;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PessoaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8826148077458704971L;

    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @Date(message = "o campo data de nascimento está inválido")
    private String dataNascimento;

    public List<EnderecoRequest> listaDeEnderecos() {
        return null;
    }

}
