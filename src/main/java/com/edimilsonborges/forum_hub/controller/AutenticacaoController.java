package com.edimilsonborges.forum_hub.controller;


import com.edimilsonborges.forum_hub.dto.autenticacao.DadosAutenticacao;
import com.edimilsonborges.forum_hub.dto.status.DadosErros;
import com.edimilsonborges.forum_hub.security.DadosTokenJWT;
import com.edimilsonborges.forum_hub.models.Usuario;
import com.edimilsonborges.forum_hub.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Tag(name = "Publico Controller", description = "Endpoints Públicos")
    @Operation(summary = "Faça login", description = "Faça login na API e gere um Token JWT e insira em Authorize para ter acesso aos endpoints restritos")
    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao) {

        Authentication Authenticationtoken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.email(), dadosAutenticacao.senha());
        Authentication authentication = authenticationManager.authenticate(Authenticationtoken);
        String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        if(tokenJWT == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("Erro ao gerar token JWT!"));
        }
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
