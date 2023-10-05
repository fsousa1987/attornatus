package com.github.fsousa1987.attornatus.api.request.pessoa;

import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@EqualsAndHashCode(callSuper = true)
public class SalvarPessoaRequest extends PessoaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -3040085526585080306L;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<AdicionarEnderecoRequest> enderecos;

    @Override
    public List<AdicionarEnderecoRequest> listaDeEnderecos() {
        return enderecos;
    }
}
