package com.edimilsonborges.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(@NotBlank String mensagem, @NotNull Long topicoId) {
}
