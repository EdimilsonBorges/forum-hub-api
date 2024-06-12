package com.edimilsonborges.forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(@NotBlank String email, @NotBlank String senha) {
}
