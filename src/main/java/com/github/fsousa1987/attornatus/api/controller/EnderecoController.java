package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecoRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.AdicionarEnderecosLoteRequest;
import com.github.fsousa1987.attornatus.api.request.endereco.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoLoteResponse;
import com.github.fsousa1987.attornatus.api.response.endereco.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/enderecos", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping(value = "/pessoas/{idPessoa}/inclusao", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoResponse> adicionarEndereco(
            @PathVariable Long idPessoa, @RequestBody @Valid AdicionarEnderecoRequest request) {

        var enderecoResponse = enderecoService.adicionarEndereco(idPessoa, request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enderecoResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(enderecoResponse);
    }

    @PostMapping(value = "/pessoas/{idPessoa}/inclusao/lote", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoLoteResponse> adicionarEnderecosEmLote(
            @PathVariable Long idPessoa, @RequestBody @Valid AdicionarEnderecosLoteRequest request) {

        var enderecoLoteResponse = enderecoService.adicionarEnderecosEmLote(idPessoa, request.getEnderecos());
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoLoteResponse);
    }

    @GetMapping(value = "/pessoas/{idPessoa}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoLoteResponse> listar(@PathVariable Long idPessoa) {
        var enderecoLoteResponse = enderecoService.listarEnderecos(idPessoa);
        return ResponseEntity.ok().body(enderecoLoteResponse);
    }

    @PutMapping(value = "/{idEndereco}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoResponse> atualizar(
            @PathVariable Long idEndereco, @RequestBody @Valid EnderecoRequest request) {

        var enderecoResponse = enderecoService.atualizarEndereco(idEndereco, request);
        return ResponseEntity.ok().body(enderecoResponse);
    }

    @PutMapping(value = "/{idEndereco}/alteracao/principal")
    public ResponseEntity<EnderecoResponse> alterarPrincipal(@PathVariable Long idEndereco) {
        var enderecoResponse = enderecoService.alterarPrincipal(idEndereco);
        return ResponseEntity.ok().body(enderecoResponse);
    }

}
