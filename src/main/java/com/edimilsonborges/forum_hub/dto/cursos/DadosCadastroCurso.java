package com.edimilsonborges.forum_hub.dto.cursos;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCurso(@NotBlank String nome, @NotBlank String categoria) {
}
