package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.dto.respostas.DadosAtualizacaoResposta;
import com.edimilsonborges.forum_hub.dto.respostas.DadosCadastroResposta;
import com.edimilsonborges.forum_hub.dto.respostas.DadosListagemResposta;
import com.edimilsonborges.forum_hub.service.RespostaService;
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
@RequestMapping("respostas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Resposta Controller", description = "Endpoints para as respostas dos tópicos")
public class RespostaController {

    @Autowired
    RespostaService respostaService;
    @Operation(summary = "Cadastre a resposta de um tópico", description = "Cadastre a resposta de um tópico")
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarResposta(@RequestBody @Valid DadosCadastroResposta dadosCadastroResposta, UriComponentsBuilder uriComponentsBuilder){
       return respostaService.cadastrarResposta(dadosCadastroResposta,uriComponentsBuilder);
    }

    @Operation(summary = "Liste todas as respostas", description = "Liste todas as respostas")
    @GetMapping
    public ResponseEntity<Page<DadosListagemResposta>> listarTodasRespostas(@PageableDefault(size = 10, sort = {"dataCriacao"})Pageable pageable) {
        return respostaService.listarTodasRespostas(pageable);
    }

    @Operation(summary = "Liste uma resposta cadastrada", description = "Liste uma resposta cadastrada")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarResposta(@PathVariable(name = "id") UUID id){
        return respostaService.listarResposta(id);
    }

    @Operation(summary = "Atualize uma resposta cadastrada por você", description = "Atualize uma resposta cadastrada por você")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarResposta(@RequestBody DadosAtualizacaoResposta dadosAtualizacaoResposta, @PathVariable(name = "id") UUID id){
        return respostaService.atualizarResposta(dadosAtualizacaoResposta,id);
    }

    @Operation(summary = "Delete uma resposta cadastrada por você", description = "Delete uma resposta cadastrada por você")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirResposta(@PathVariable(value = "id") UUID id){
        return respostaService.excluirResposta(id);
    }
}
