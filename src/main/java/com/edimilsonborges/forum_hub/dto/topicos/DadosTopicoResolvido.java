package com.edimilsonborges.forum_hub.dto.topicos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DadosTopicoResolvido(@NotNull UUID respostaId) {
}
