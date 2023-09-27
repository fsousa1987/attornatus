package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.service.impl.EnderecoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoServiceImpl enderecoService;

    @PostMapping("/pessoas/inclusao/{idPessoa}")
    public ResponseEntity<EnderecoResponse> adicionar(@PathVariable Long idPessoa, @RequestBody @Valid EnderecoRequest enderecoRequest) {
        EnderecoResponse enderecoResponse = enderecoService.adicionarEndereco(idPessoa, enderecoRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enderecoResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(enderecoResponse);

    }

    @GetMapping("/pessoas/{idPessoa}")
    public ResponseEntity<List<EnderecoResponse>> listar(@PathVariable Long idPessoa) {
        List<EnderecoResponse> enderecoResponses = enderecoService.listarEnderecos(idPessoa);
        return ResponseEntity.ok().body(enderecoResponses);
    }

}
