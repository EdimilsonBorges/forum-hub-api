package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.dto.usuarios.DadosAtualizacaoUsuario;
import com.edimilsonborges.forum_hub.dto.usuarios.DadosCadastroUsuario;
import com.edimilsonborges.forum_hub.dto.usuarios.DadosListagemUsuarios;
import com.edimilsonborges.forum_hub.service.UsuarioService;
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

@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuario Controller", description = "Endpoints das contas dos usuários")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Tag(name = "Publico Controller", description = "Endpoints Públicos")
    @Operation(summary = "Crie sua conta", description = "Cria sua conta e use este email e senha no auth para se autenticar")
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriBuilder){
        return usuarioService.cadastrarUsuario(dadosCadastroUsuario,uriBuilder);
    }

    @Operation(summary = "Liste todos os usuários cadastrados", description = "Liste todos os usuários cadastrados")
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DadosListagemUsuarios>> listarTodosUsuario(@PageableDefault(size = 10)Pageable paginacao){
        return usuarioService.listarTodosUsuarios(paginacao);
    }
    @Operation(summary = "Liste um usuário cadastrado", description = "Liste um usuario cadastrado")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> listarUsuario(@PathVariable(value = "id") Long id){
        return usuarioService.listarUsuario(id);
    }

    @Operation(summary = "Atualize informações de um usuário", description = "Atualize informações de um usuário")
    @PutMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> atualizarUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dadosAtualizacaoUsuario, @PathVariable(value = "id") Long id){
        return usuarioService.atualizarUsuario(dadosAtualizacaoUsuario, id);
    }

    @Operation(summary = "Delete sua conta de usuário", description = "Delete sua conta de usuário")
    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> cancelarConta(@PathVariable(value = "id") Long id){
        return usuarioService.cancelarConta(id);
    }
}
