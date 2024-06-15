package com.edimilsonborges.forum_hub.dto.usuarios;

import com.edimilsonborges.forum_hub.models.Topico;

import java.util.List;
import java.util.UUID;

public record DadosUsuario(UUID id, String nome, String email, String senha, List<Topico>topicos) {
}
