package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.pessoa.AtualizarPessoaRequest;
import com.github.fsousa1987.attornatus.api.request.pessoa.SalvarPessoaRequest;
import com.github.fsousa1987.attornatus.api.response.PessoaResponse;
import com.github.fsousa1987.attornatus.domain.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaResponse> adicionar(@Valid @RequestBody SalvarPessoaRequest salvarPessoaRequest) {
        PessoaResponse pessoaResponse = pessoaService.salvarPessoa(salvarPessoaRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoaResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(pessoaResponse);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarPessoaRequest atualizarPessoaRequest) {
        pessoaService.atualizarPessoa(id, atualizarPessoaRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaResponse> consultar(@PathVariable Long id) {
        PessoaResponse pessoaResponse = pessoaService.buscarPorId(id);
        return ResponseEntity.ok().body(pessoaResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PessoaResponse>> consultar() {
        List<PessoaResponse> pessoaResponses = pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoaResponses);
    }

}
