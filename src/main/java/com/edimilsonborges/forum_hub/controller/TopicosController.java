package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.dto.DadosAtualizacaoTopico;
import com.edimilsonborges.forum_hub.dto.DadosCadastroTopicos;
import com.edimilsonborges.forum_hub.dto.DadosListagemTopicos;
import com.edimilsonborges.forum_hub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos")
public class TopicosController {
    @Autowired
    TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> CadastrarTopico(@RequestBody @Valid DadosCadastroTopicos dadosCadastroTopicos, UriComponentsBuilder uriBuilder) {
        return topicoService.cadastrarTopico(dadosCadastroTopicos, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listarTodosTopicos(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        return topicoService.listarTodosTopicos(paginacao);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> listarTopico(@PathVariable(name = "id") Long id){
       return topicoService.listarTopico(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarTopico(@RequestBody @Valid DadosAtualizacaoTopico dadosAtualizacaoTopico, @PathVariable(name = "id") Long id){
        return topicoService.atualizarTopico(dadosAtualizacaoTopico, id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirTopico(@PathVariable(name = "id") Long id){
       return topicoService.excluirTopico(id);
    }
}
