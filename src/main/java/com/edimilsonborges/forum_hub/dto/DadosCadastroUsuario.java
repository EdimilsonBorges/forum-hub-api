package com.edimilsonborges.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(@NotBlank String nome, @NotBlank String email, @NotBlank String senha) {
}
