package com.github.fsousa1987.attornatus.api.request.pessoa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecoRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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

    @NotNull(message = "data de nascimento é um campo obrigatório")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    public List<AdicionarEnderecoRequest> listaDeEnderecos() {
        return null;
    }

}
