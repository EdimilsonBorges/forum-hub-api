package com.edimilsonborges.forum_hub.dto.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(@NotBlank String nome, @NotBlank String email, @NotBlank String senha) {
}
