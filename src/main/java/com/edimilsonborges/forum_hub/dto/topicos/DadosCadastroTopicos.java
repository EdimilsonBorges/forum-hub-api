package com.edimilsonborges.forum_hub.dto.topicos;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTopicos(@NotBlank String titulo, @NotBlank String mensagem, @NotBlank String curso) {
}
