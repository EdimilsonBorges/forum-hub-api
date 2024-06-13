package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.dto.DadosAtualizacaoResposta;
import com.edimilsonborges.forum_hub.dto.DadosCadastroResposta;
import com.edimilsonborges.forum_hub.dto.DadosListagemResposta;
import com.edimilsonborges.forum_hub.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("respostas")
public class RespostaController {

    @Autowired
    RespostaService respostaService;
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarResposta(@RequestBody @Valid DadosCadastroResposta dadosCadastroResposta, UriComponentsBuilder uriComponentsBuilder){
       return respostaService.cadastrarResposta(dadosCadastroResposta,uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemResposta>> listarTodasRespostas(Pageable pageable) {
        return respostaService.listarTodasRespostas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarResposta(@PathVariable(name = "id") Long id){
        return respostaService.listarResposta(id);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarResposta(@RequestBody DadosAtualizacaoResposta dadosAtualizacaoResposta, @PathVariable(name = "id") Long id){
        return respostaService.atualizarResposta(dadosAtualizacaoResposta,id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirResposta(@PathVariable(value = "id") Long id){
        return respostaService.excluirResposta(id);
    }
}
