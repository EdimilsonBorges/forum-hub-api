package com.edimilsonborges.forum_hub.dto.cursos;

import com.edimilsonborges.forum_hub.models.Categoria;
import com.edimilsonborges.forum_hub.models.Curso;

import java.util.List;
import java.util.UUID;

public record DadosCursos(UUID id, String nome, Categoria categoria, List<Curso>cursos) {
}
