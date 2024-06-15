package com.edimilsonborges.forum_hub.dto.usuarios;

import com.edimilsonborges.forum_hub.models.Usuario;
import org.springframework.hateoas.Links;

import java.util.UUID;

public record DadosListagemUsuarios(UUID id, String nome, Links links) {
    public DadosListagemUsuarios(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLinks());
    }
}
