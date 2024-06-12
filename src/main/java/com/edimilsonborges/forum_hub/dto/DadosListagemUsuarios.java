package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.models.Usuario;

public record DadosListagemUsuarios(Long id, String nome) {
    public DadosListagemUsuarios(Usuario usuario) {
        this(usuario.getId(), usuario.getNome());
    }
}
