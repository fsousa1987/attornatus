package com.github.fsousa1987.attornatus.api.controller;

import com.github.fsousa1987.attornatus.api.request.AdicionarEnderecosLoteRequest;
import com.github.fsousa1987.attornatus.api.request.EnderecoRequest;
import com.github.fsousa1987.attornatus.api.response.EnderecoResponse;
import com.github.fsousa1987.attornatus.domain.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping("/pessoas/{idPessoa}/inclusao")
    public ResponseEntity<EnderecoResponse> adicionarEndereco(@PathVariable Long idPessoa, @RequestBody @Valid EnderecoRequest enderecoRequest) {
        EnderecoResponse enderecoResponse = enderecoService.adicionarEndereco(idPessoa, enderecoRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enderecoResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(enderecoResponse);
    }

    @PostMapping("/pessoas/{idPessoa}/inclusao/lote")
    public ResponseEntity<List<EnderecoResponse>> adicionarEnderecosEmLote(@PathVariable Long idPessoa,
                                                                           @RequestBody @Valid Set<AdicionarEnderecosLoteRequest> enderecosRequest) {
        List<EnderecoResponse> enderecoResponses = enderecoService.adicionarEnderecosEmLote(idPessoa, enderecosRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoResponses);
    }

    @GetMapping("/pessoas/{idPessoa}")
    public ResponseEntity<List<EnderecoResponse>> listar(@PathVariable Long idPessoa) {
        List<EnderecoResponse> enderecoResponses = enderecoService.listarEnderecos(idPessoa);
        return ResponseEntity.ok().body(enderecoResponses);
    }

}
