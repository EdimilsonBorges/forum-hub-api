package com.edimilsonborges.forum_hub.dto.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(@NotBlank String email, @NotBlank String senha) {
}
