package com.edimilsonborges.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTopicos(@NotBlank String titulo, @NotBlank String mensagem, @NotBlank String curso) {
}
