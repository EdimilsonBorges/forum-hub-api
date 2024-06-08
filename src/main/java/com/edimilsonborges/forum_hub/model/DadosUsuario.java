package com.edimilsonborges.forum_hub.model;

import java.util.List;

public record DadosUsuario(Long id, String nome, String email, String senha, List<Topico>topicos) {
}
