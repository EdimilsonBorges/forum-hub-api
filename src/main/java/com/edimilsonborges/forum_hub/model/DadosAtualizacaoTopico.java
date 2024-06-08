package com.edimilsonborges.forum_hub.model;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(@NotBlank String titulo, @NotBlank String mensagem, String status) {
}
