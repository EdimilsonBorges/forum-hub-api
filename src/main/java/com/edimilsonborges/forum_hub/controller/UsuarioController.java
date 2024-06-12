package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.dto.DadosAtualizacaoUsuario;
import com.edimilsonborges.forum_hub.dto.DadosCadastroUsuario;
import com.edimilsonborges.forum_hub.dto.DadosListagemUsuarios;
import com.edimilsonborges.forum_hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriBuilder){
        return usuarioService.cadastrarUsuario(dadosCadastroUsuario,uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuarios>> listarTodosUsuario(Pageable paginacao){
        return usuarioService.listarTodosUsuarios(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarUsuario(@PathVariable(value = "id") Long id){
        return usuarioService.listarUsuario(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario, @PathVariable(value = "id") Long id){
        return usuarioService.atualizarUsuario(dadosAtualizacaoUsuario, id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> cancelarConta(@PathVariable(value = "id") Long id){
        return usuarioService.cancelarConta(id);
    }
}
