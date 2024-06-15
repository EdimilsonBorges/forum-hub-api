package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.dto.topicos.DadosAtualizacaoTopico;
import com.edimilsonborges.forum_hub.dto.topicos.DadosCadastroTopicos;
import com.edimilsonborges.forum_hub.dto.topicos.DadosListagemTopicos;
import com.edimilsonborges.forum_hub.dto.topicos.DadosTopicoResolvido;
import com.edimilsonborges.forum_hub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topico Controller", description = "Endpoints dos tópicos de perguntas")
public class TopicoController {
    @Autowired
    TopicoService topicoService;

    @Operation(summary = "Crie um tópico informando sua dúvida", description = "Crie um tópico informando sua dúvida")
    @PostMapping
    @Transactional
    public ResponseEntity<?> CadastrarTopico(@RequestBody @Valid DadosCadastroTopicos dadosCadastroTopicos, UriComponentsBuilder uriBuilder) {
        return topicoService.cadastrarTopico(dadosCadastroTopicos, uriBuilder);
    }

    @Operation(summary = "Liste todos os tópicos cadastrados", description = "Liste todos os tópicos cadastrados")
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listarTodosTopicos(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        return topicoService.listarTodosTopicos(paginacao);
    }
    @Operation(summary = "Liste um tópicos cadastrado", description = "Liste um tópicos cadastrado")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarTopico(@PathVariable(name = "id") UUID id){
       return topicoService.listarTopico(id);
    }

    @Operation(summary = "Atualize os dados de um tópicos cadastrado", description = "Atualize os dados de um tópicos cadastrado")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarTopico(@RequestBody @Valid DadosAtualizacaoTopico dadosAtualizacaoTopico, @PathVariable(name = "id") UUID id){
        return topicoService.atualizarTopico(dadosAtualizacaoTopico, id);
    }

    @Operation(summary = "Delete um tópicos cadastrado por você", description = "Delete um tópicos cadastrado por você")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirTopico(@PathVariable(name = "id") UUID id){
       return topicoService.excluirTopico(id);
    }

    @Operation(summary = "Altere os status do tópico para resolvido quando o tópico for resolvido", description = "Informe o id do tópico resolvido e abaixo o id da resposta onde teve a solução")
    @PutMapping("resolvido/{id}")
    @Transactional
    public ResponseEntity<?> atualizarStatusTopico(@RequestBody @Valid DadosTopicoResolvido dadosTopicoResolvido, @PathVariable(name = "id") UUID id){
        return topicoService.atualizarStatusTopico(id, dadosTopicoResolvido);
    }
}
