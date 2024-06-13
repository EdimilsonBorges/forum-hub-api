package com.edimilsonborges.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(@NotBlank String titulo, @NotBlank String mensagem) {
}
