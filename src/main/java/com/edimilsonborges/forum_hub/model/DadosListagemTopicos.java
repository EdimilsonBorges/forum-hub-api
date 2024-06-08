package com.edimilsonborges.forum_hub.model;

import java.time.LocalDateTime;

public record DadosListagemTopicos(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        String autor,
        String curso) {
}
