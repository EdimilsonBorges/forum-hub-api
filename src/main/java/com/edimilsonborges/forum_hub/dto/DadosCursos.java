package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.models.Categoria;
import com.edimilsonborges.forum_hub.models.Curso;

import java.util.List;
public record DadosCursos(Long id, String nome, Categoria categoria, List<Curso>cursos) {
}
